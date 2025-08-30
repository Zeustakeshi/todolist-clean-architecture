package com.abc.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abc.myapplication.domain.entity.Todo
import com.abc.myapplication.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TodoUiState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val showOnlyCompleted: Boolean = false
)

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase,
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    private val insertTodoUseCase: InsertTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val deleteAllTodosUseCase: DeleteAllTodosUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TodoUiState())
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _showOnlyCompleted = MutableStateFlow(false)
    val showOnlyCompleted: StateFlow<Boolean> = _showOnlyCompleted.asStateFlow()
    
    init {
        loadTodos()
    }
    
    private fun loadTodos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                combine(
                    getAllTodosUseCase(),
                    _searchQuery,
                    _showOnlyCompleted
                ) { todos, query, showCompleted ->
                    var filteredTodos = todos
                    
                    // Filter by completion status
                    if (showCompleted) {
                        filteredTodos = filteredTodos.filter { it.isCompleted }
                    }
                    
                    // Filter by search query
                    if (query.isNotBlank()) {
                        filteredTodos = filteredTodos.filter { todo ->
                            todo.title.contains(query, ignoreCase = true) ||
                            todo.description.contains(query, ignoreCase = true)
                        }
                    }
                    
                    filteredTodos
                }.collect { filteredTodos ->
                    _uiState.value = _uiState.value.copy(
                        todos = filteredTodos,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
    
    fun insertTodo(title: String, description: String) {
        viewModelScope.launch {
            try {
                val todo = Todo(
                    title = title.trim(),
                    description = description.trim()
                )
                insertTodoUseCase(todo)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message
                )
            }
        }
    }
    
    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                updateTodoUseCase(todo)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message
                )
            }
        }
    }
    
    fun toggleTodoCompletion(todo: Todo) {
        viewModelScope.launch {
            try {
                val updatedTodo = todo.copy(isCompleted = !todo.isCompleted)
                updateTodoUseCase(updatedTodo)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message
                )
            }
        }
    }
    
    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                deleteTodoUseCase(todo)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message
                )
            }
        }
    }
    
    fun deleteAllTodos() {
        viewModelScope.launch {
            try {
                deleteAllTodosUseCase()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message
                )
            }
        }
    }
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun toggleShowOnlyCompleted() {
        _showOnlyCompleted.value = !_showOnlyCompleted.value
    }
    
    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

package com.abc.myapplication.data.repository

import com.abc.myapplication.data.local.dao.TodoDao
import com.abc.myapplication.data.local.entity.toDomainModel
import com.abc.myapplication.data.local.entity.toEntity
import com.abc.myapplication.domain.entity.Todo
import com.abc.myapplication.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {
    
    override fun getAllTodos(): Flow<List<Todo>> {
        return todoDao.getAllTodos().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override suspend fun getTodoById(id: Long): Todo? {
        return todoDao.getTodoById(id)?.toDomainModel()
    }
    
    override suspend fun insertTodo(todo: Todo): Long {
        return todoDao.insertTodo(todo.toEntity())
    }
    
    override suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo.toEntity())
    }
    
    override suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo.toEntity())
    }
    
    override suspend fun deleteAllTodos() {
        todoDao.deleteAllTodos()
    }
}

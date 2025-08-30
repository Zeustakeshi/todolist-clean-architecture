package com.abc.myapplication.domain.usecase

import com.abc.myapplication.domain.entity.Todo
import com.abc.myapplication.domain.repository.TodoRepository
import java.util.Date
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo) {
        if (todo.title.isBlank()) {
            throw IllegalArgumentException("Todo title cannot be empty")
        }
        val updatedTodo = todo.copy(updatedAt = Date())
        todoRepository.updateTodo(updatedTodo)
    }
}

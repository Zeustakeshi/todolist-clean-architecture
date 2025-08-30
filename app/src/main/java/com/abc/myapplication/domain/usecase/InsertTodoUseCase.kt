package com.abc.myapplication.domain.usecase

import com.abc.myapplication.domain.entity.Todo
import com.abc.myapplication.domain.repository.TodoRepository
import javax.inject.Inject

class InsertTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo): Long {
        if (todo.title.isBlank()) {
            throw IllegalArgumentException("Todo title cannot be empty")
        }
        return todoRepository.insertTodo(todo)
    }
}

package com.abc.myapplication.domain.usecase

import com.abc.myapplication.domain.entity.Todo
import com.abc.myapplication.domain.repository.TodoRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo) {
        todoRepository.deleteTodo(todo)
    }
}

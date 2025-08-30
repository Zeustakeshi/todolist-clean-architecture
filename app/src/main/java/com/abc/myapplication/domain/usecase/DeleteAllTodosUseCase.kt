package com.abc.myapplication.domain.usecase

import com.abc.myapplication.domain.repository.TodoRepository
import javax.inject.Inject

class DeleteAllTodosUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke() {
        todoRepository.deleteAllTodos()
    }
}

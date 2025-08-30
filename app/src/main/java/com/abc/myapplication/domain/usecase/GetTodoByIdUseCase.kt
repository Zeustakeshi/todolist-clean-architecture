package com.abc.myapplication.domain.usecase

import com.abc.myapplication.domain.entity.Todo
import com.abc.myapplication.domain.repository.TodoRepository
import javax.inject.Inject

class GetTodoByIdUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(id: Long): Todo? {
        return todoRepository.getTodoById(id)
    }
}

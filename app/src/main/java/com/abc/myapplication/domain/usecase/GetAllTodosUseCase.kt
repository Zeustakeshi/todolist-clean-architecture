package com.abc.myapplication.domain.usecase

import com.abc.myapplication.domain.entity.Todo
import com.abc.myapplication.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTodosUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(): Flow<List<Todo>> {
        return todoRepository.getAllTodos()
    }
}

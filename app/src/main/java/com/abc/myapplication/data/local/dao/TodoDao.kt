package com.abc.myapplication.data.local.dao

import androidx.room.*
import com.abc.myapplication.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    
    @Query("SELECT * FROM todos ORDER BY createdAt DESC")
    fun getAllTodos(): Flow<List<TodoEntity>>
    
    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Long): TodoEntity?
    
    @Insert
    suspend fun insertTodo(todo: TodoEntity): Long
    
    @Update
    suspend fun updateTodo(todo: TodoEntity)
    
    @Delete
    suspend fun deleteTodo(todo: TodoEntity)
    
    @Query("DELETE FROM todos")
    suspend fun deleteAllTodos()
    
    @Query("SELECT * FROM todos WHERE isCompleted = :isCompleted ORDER BY createdAt DESC")
    fun getTodosByStatus(isCompleted: Boolean): Flow<List<TodoEntity>>
}

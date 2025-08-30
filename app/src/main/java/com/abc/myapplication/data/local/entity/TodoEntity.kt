package com.abc.myapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abc.myapplication.domain.entity.Todo
import java.util.Date

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)

// Extension functions for mapping
fun TodoEntity.toDomainModel(): Todo {
    return Todo(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = Date(createdAt),
        updatedAt = Date(updatedAt)
    )
}

fun Todo.toEntity(): TodoEntity {
    return TodoEntity(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt.time,
        updatedAt = updatedAt.time
    )
}

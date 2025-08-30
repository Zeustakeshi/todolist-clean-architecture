package com.abc.myapplication.presentation.navigation

sealed class Screen(val route: String) {
    object TodoList : Screen("todo_list")
    object AddEditTodo : Screen("add_edit_todo")
    
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

package com.abc.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abc.myapplication.presentation.screen.AddEditTodoScreen
import com.abc.myapplication.presentation.screen.TodoListScreen

@Composable
fun TodoNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.TodoList.route,
        modifier = modifier
    ) {
        composable(route = Screen.TodoList.route) {
            TodoListScreen(
                onNavigateToAddTodo = {
                    navController.navigate(Screen.AddEditTodo.route)
                },
                onNavigateToEditTodo = { todoId ->
                    navController.navigate(Screen.AddEditTodo.withArgs(todoId.toString()))
                }
            )
        }
        
        composable(
            route = Screen.AddEditTodo.route + "?todoId={todoId}",
            arguments = listOf(
                navArgument("todoId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getLong("todoId") ?: -1L
            AddEditTodoScreen(
                todoId = if (todoId == -1L) null else todoId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

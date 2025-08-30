package com.abc.myapplication.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abc.myapplication.presentation.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoScreen(
    todoId: Long?,
    onNavigateBack: () -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    // Load todo if editing
    LaunchedEffect(todoId) {
        if (todoId != null && todoId != -1L) {
            // In a real implementation, you'd have a getTodoById method in ViewModel
            // For now, we'll just keep it simple
        }
    }
    
    // Handle save result
    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage == null && !uiState.isLoading && isLoading) {
            onNavigateBack()
        }
        isLoading = uiState.isLoading
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            
            Text(
                text = if (todoId == null) "Add Todo" else "Edit Todo",
                style = MaterialTheme.typography.headlineMedium
            )
            
            IconButton(
                onClick = {
                    if (title.isNotBlank()) {
                        isLoading = true
                        if (todoId == null) {
                            viewModel.insertTodo(title, description)
                        } else {
                            // For editing, you'd need to implement updateTodo with id
                            // For now, just insert new
                            viewModel.insertTodo(title, description)
                        }
                    }
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = "Save")
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Title input
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Description input
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Save button
        Button(
            onClick = {
                if (title.isNotBlank()) {
                    isLoading = true
                    if (todoId == null) {
                        viewModel.insertTodo(title, description)
                    } else {
                        // For editing implementation
                        viewModel.insertTodo(title, description)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = title.isNotBlank() && !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(if (todoId == null) "Add Todo" else "Update Todo")
            }
        }
        
        // Error message
        uiState.errorMessage?.let { errorMessage ->
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

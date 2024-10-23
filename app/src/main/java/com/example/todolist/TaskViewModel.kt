package com.example.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolist.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class TaskViewModel(id: UUID) : ViewModel() {
    private val repository = TaskRepository.get()

    private val _task: MutableStateFlow<Task?> = MutableStateFlow(null)
    val task: StateFlow<Task?> = _task.asStateFlow()

    init {
        viewModelScope.launch {
            _task.value = repository.getTask(id)
        }
    }
}

class TaskViewModelFactory(
    private val id: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(id) as T
    }
}
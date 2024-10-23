package com.example.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {
    private val repository: TaskRepository = TaskRepository.get()

    private val _tasks: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val tasks: StateFlow<List<Task>>
        get() = _tasks.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getTasks().collect {
                _tasks.value = it
            }
        }
    }

    suspend fun addTask(task: Task) {
        repository.addTask(task)
    }


}
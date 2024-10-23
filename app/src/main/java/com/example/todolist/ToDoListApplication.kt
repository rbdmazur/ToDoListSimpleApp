package com.example.todolist

import android.app.Application

class ToDoListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TaskRepository.initialize(this)
    }
}
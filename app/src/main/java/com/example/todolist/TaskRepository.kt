package com.example.todolist

import android.content.Context
import androidx.room.Room
import com.example.todolist.database.TaskDatabase
import com.example.todolist.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID

private const val DATABASE_NAME = "task-database"

class TaskRepository private constructor(context: Context) {

    private val database: TaskDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            TaskDatabase :: class.java,
            DATABASE_NAME
        )
        .build()

    companion object {
        private var INSTANCE: TaskRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }
        }

        fun get(): TaskRepository {
            return INSTANCE ?:
            throw IllegalStateException("TaskRepository must be initialized")
        }
    }

    fun getTasks(): Flow<List<Task>> = database.taskDao().getTasks()
    suspend fun getTask(id: UUID): Task = database.taskDao().getTask(id)
    suspend fun addTask(task: Task) {
        database.taskDao().addTask(task)
    }
}
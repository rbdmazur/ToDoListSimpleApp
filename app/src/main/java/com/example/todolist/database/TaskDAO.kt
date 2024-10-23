package com.example.todolist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todolist.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TaskDAO {

    @Query("SELECT * FROM Task")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE id=(:id)")
    suspend fun getTask(id: UUID): Task

    @Insert
    suspend fun addTask(task: Task)
}
package com.example.todolist.database

import androidx.room.TypeConverter
import java.util.Date

class TaskTypeConverter {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(milliseconds: Long): Date {
        return Date(milliseconds)
    }
}
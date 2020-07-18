package com.pratthamarora.todoapp.data.db

import androidx.room.TypeConverter
import com.pratthamarora.todoapp.data.model.Priority

class MyConverters {

    @TypeConverter
    fun fromPriority(priority: Priority): String = priority.name

    @TypeConverter
    fun toPriority(priority: String): Priority = Priority.valueOf(priority)
}
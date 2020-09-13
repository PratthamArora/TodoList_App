package com.pratthamarora.todoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pratthamarora.todoapp.data.model.TodoData

@Database(entities = [TodoData::class], version = 2, exportSchema = false)
@TypeConverters(MyConverters::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun toDoDao(): TodoDao

}
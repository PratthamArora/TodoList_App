package com.pratthamarora.todoapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pratthamarora.todoapp.data.model.TodoList

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
     fun getAllData(): LiveData<List<TodoList>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(todo: TodoList)
}
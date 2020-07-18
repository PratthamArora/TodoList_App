package com.pratthamarora.todoapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pratthamarora.todoapp.data.model.TodoData

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<TodoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(todo: TodoData)

    @Update
    suspend fun updateData(todo: TodoData)
}
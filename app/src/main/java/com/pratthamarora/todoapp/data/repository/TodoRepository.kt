package com.pratthamarora.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.pratthamarora.todoapp.data.db.TodoDao
import com.pratthamarora.todoapp.data.model.TodoData
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao) {

    val getAllData = todoDao.getAllData()
    val sortByHighPriority = todoDao.sortByHighPriority()
    val sortByLowPriority = todoDao.sortByLowPriority()

    suspend fun insertData(todo: TodoData) {
        todoDao.insertData(todo)
    }

    suspend fun updateData(todo: TodoData) {
        todoDao.updateData(todo)
    }

    suspend fun deleteData(todo: TodoData) {
        todoDao.deleteData(todo)
    }

    suspend fun deleteAllData() {
        todoDao.deleteAllData()
    }

    fun searchData(query: String): LiveData<List<TodoData>> {
        return todoDao.searchData(query)
    }

}
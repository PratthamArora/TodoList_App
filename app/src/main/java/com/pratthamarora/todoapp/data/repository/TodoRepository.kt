package com.pratthamarora.todoapp.data.repository

import com.pratthamarora.todoapp.data.db.TodoDao
import com.pratthamarora.todoapp.data.model.TodoData

class TodoRepository(private val todoDao: TodoDao) {

    val getAllData = todoDao.getAllData()

    suspend fun insertData(todo: TodoData) {
        todoDao.insertData(todo)
    }

    suspend fun updateData(todo: TodoData) {
        todoDao.updateData(todo)
    }

    suspend fun deleteData(todo: TodoData) {
        todoDao.deleteData(todo)
    }

}
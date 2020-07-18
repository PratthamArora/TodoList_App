package com.pratthamarora.todoapp.data.repository

import com.pratthamarora.todoapp.data.db.TodoDao
import com.pratthamarora.todoapp.data.model.TodoList

class TodoRepository(private val todoDao: TodoDao) {

    val getAllData = todoDao.getAllData()

    suspend fun insertData(todo: TodoList) {
        todoDao.insertData(todo)
    }
}
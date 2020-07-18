package com.pratthamarora.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pratthamarora.todoapp.data.db.TodoDatabase
import com.pratthamarora.todoapp.data.model.TodoList
import com.pratthamarora.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val todoDao = TodoDatabase.getDatabase(application).toDoDao()
    private val repository: TodoRepository
    private val getAllData: LiveData<List<TodoList>>

    init {
        repository = TodoRepository(todoDao)
        getAllData = repository.getAllData
    }

    fun insertData(todo: TodoList) {
        viewModelScope.launch(IO) {
            repository.insertData(todo)
        }
    }
}
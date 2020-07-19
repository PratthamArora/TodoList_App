package com.pratthamarora.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pratthamarora.todoapp.data.db.TodoDatabase
import com.pratthamarora.todoapp.data.model.TodoData
import com.pratthamarora.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val todoDao = TodoDatabase.getDatabase(application).toDoDao()
    private val repository: TodoRepository
    val getAllData: LiveData<List<TodoData>>

    init {
        repository = TodoRepository(todoDao)
        getAllData = repository.getAllData
    }

    fun insertData(todo: TodoData) {
        viewModelScope.launch(IO) {
            repository.insertData(todo)
        }
    }

    fun updateData(todo: TodoData) {
        viewModelScope.launch(IO) {
            repository.updateData(todo)
        }
    }

    fun deleteData(todo: TodoData) {
        viewModelScope.launch(IO) {
            repository.deleteData(todo)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(IO) {
            repository.deleteAllData()
        }
    }

    fun searchData(query: String): LiveData<List<TodoData>> {
        return repository.searchData(query)
    }
}
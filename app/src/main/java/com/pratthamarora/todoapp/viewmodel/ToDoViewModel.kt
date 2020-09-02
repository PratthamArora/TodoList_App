package com.pratthamarora.todoapp.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratthamarora.todoapp.data.model.TodoData
import com.pratthamarora.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ToDoViewModel @ViewModelInject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val getAllData: LiveData<List<TodoData>> = repository.getAllData
    val sortByHighPriority: LiveData<List<TodoData>> = repository.sortByHighPriority
    val sortByLowPriority: LiveData<List<TodoData>> = repository.sortByLowPriority

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
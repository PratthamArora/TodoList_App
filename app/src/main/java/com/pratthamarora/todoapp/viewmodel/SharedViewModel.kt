package com.pratthamarora.todoapp.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import com.pratthamarora.todoapp.data.model.Priority

class SharedViewModel(application: Application):AndroidViewModel(application) {

     fun checkData(title: String, description: String): Boolean {
        return if ((TextUtils.isEmpty(title) || TextUtils.isEmpty(description))) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

     fun setPriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            "Low Priority" -> Priority.LOW
            else -> Priority.LOW
        }
    }
}
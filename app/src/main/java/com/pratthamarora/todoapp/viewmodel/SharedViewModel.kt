package com.pratthamarora.todoapp.viewmodel

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pratthamarora.todoapp.R
import com.pratthamarora.todoapp.data.model.Priority
import com.pratthamarora.todoapp.data.model.TodoData
import dagger.hilt.android.qualifiers.ApplicationContext

class SharedViewModel @ViewModelInject constructor(
    @ApplicationContext application: Context
) : ViewModel() {

    val emptyDB: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkEmptyDB(todoData: List<TodoData>) {
        emptyDB.value = todoData.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.red
                        )
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.yellow
                        )
                    )
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.green
                        )
                    )
                }

            }
        }

    }

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
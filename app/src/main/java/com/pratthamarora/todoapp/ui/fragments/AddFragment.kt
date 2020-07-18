package com.pratthamarora.todoapp.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pratthamarora.todoapp.R
import com.pratthamarora.todoapp.data.model.Priority
import com.pratthamarora.todoapp.data.model.TodoList
import com.pratthamarora.todoapp.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private val viewModel by viewModels<ToDoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_menu -> {
                insertData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertData() {
        val title = titleET.text.toString()
        val priority = spinnerPriority.selectedItem.toString()
        val desc = descriptionET.text.toString()
        val validate = checkData(title, desc)
        if (validate) {
            val newTodo = TodoList(
                0,
                title,
                setPriority(priority),
                desc
            )
            viewModel.insertData(newTodo)
            Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun checkData(title: String, description: String): Boolean {
        return if ((TextUtils.isEmpty(title) || TextUtils.isEmpty(description))) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    private fun setPriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            "Low Priority" -> Priority.LOW
            else -> Priority.LOW
        }
    }
}
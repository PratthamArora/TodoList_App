package com.pratthamarora.todoapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pratthamarora.todoapp.R
import com.pratthamarora.todoapp.data.model.TodoData
import com.pratthamarora.todoapp.utils.Utility.hideTheKeyboard
import com.pratthamarora.todoapp.viewmodel.SharedViewModel
import com.pratthamarora.todoapp.viewmodel.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddFragment : Fragment() {

    private val viewModel by viewModels<ToDoViewModel>()
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val sdf = SimpleDateFormat("MMM dd yyyy, h:mm aa", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)
        view.spinnerPriority.onItemSelectedListener = sharedViewModel.listener
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
        val validate = sharedViewModel.checkData(title, desc)
        val dateCreated = sdf.format(System.currentTimeMillis())
        if (validate) {
            requireContext().hideTheKeyboard(descriptionET as EditText)
            val newTodo = TodoData(
                0,
                title,
                sharedViewModel.setPriority(priority),
                desc,
                dateCreated
            )
            viewModel.insertData(newTodo)
            Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT)
                .show()
        }

    }


}
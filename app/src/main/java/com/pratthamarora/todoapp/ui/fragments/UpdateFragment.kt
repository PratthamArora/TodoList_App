package com.pratthamarora.todoapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pratthamarora.todoapp.R
import com.pratthamarora.todoapp.data.model.TodoData
import com.pratthamarora.todoapp.viewmodel.SharedViewModel
import com.pratthamarora.todoapp.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val viewModel by viewModels<ToDoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        setHasOptionsMenu(true)

        view.apply {
            titleETUpdate.setText(args.todo.title)
            descriptionETUpdate.setText(args.todo.description)
            spinnerPriorityUpdate.setSelection(sharedViewModel.parsePriority(args.todo.priority))
            spinnerPriorityUpdate.onItemSelectedListener = sharedViewModel.listener
        }


        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_menu -> {
                updateData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateData() {
        val title = titleETUpdate.text.toString()
        val desc = descriptionETUpdate.text.toString()
        val priority = spinnerPriorityUpdate.selectedItem.toString()
        val validate = sharedViewModel.checkData(title, desc)
        if (validate) {
            val updatedData = TodoData(
                args.todo.id,
                title,
                sharedViewModel.setPriority(priority),
                desc
            )
            viewModel.updateData(updatedData)
            Toast.makeText(requireContext(), "Todo updated successfully!", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT)
                .show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
    }

}
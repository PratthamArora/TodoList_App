package com.pratthamarora.todoapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.pratthamarora.todoapp.R
import com.pratthamarora.todoapp.data.model.Priority
import com.pratthamarora.todoapp.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel by viewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        setHasOptionsMenu(true)

        view.apply {
            titleETUpdate.setText(args.todo.title)
            descriptionETUpdate.setText(args.todo.description)
            spinnerPriorityUpdate.setSelection(parsePriority(args.todo.priority))
            spinnerPriorityUpdate.onItemSelectedListener = sharedViewModel.listener
        }


        return view
    }

    private fun parsePriority(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
    }

}
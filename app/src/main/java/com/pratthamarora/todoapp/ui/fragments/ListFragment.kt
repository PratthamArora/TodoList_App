package com.pratthamarora.todoapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pratthamarora.todoapp.R
import com.pratthamarora.todoapp.ui.adapter.TodoAdapter
import com.pratthamarora.todoapp.viewmodel.SharedViewModel
import com.pratthamarora.todoapp.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val viewModel by viewModels<ToDoViewModel>()
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val todoAdapter by lazy { TodoAdapter(arrayListOf()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = view.listRecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = todoAdapter
        }
        viewModel.getAllData.observe(viewLifecycleOwner, Observer {
            todoAdapter.setList(it)
            sharedViewModel.checkEmptyDB(it)
        })
        sharedViewModel.emptyDB.observe(viewLifecycleOwner, Observer {
            setupNoData(it)
        })
        setHasOptionsMenu(true)

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        view.listLayout.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

        return view
    }

    private fun setupNoData(it: Boolean) {
        if (it) {
            view?.apply {
                noDataImage.isVisible = true
                noDataText.isVisible = true
                listRecyclerView.isGone = true
            }
        } else {
            view?.apply {
                noDataImage.isGone = true
                noDataText.isGone = true
                listRecyclerView.isVisible = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteAll -> {
                deleteAllData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllData() {
        val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAllData()
                Toast.makeText(
                    requireContext(),
                    "Successfully Deleted All ToDos!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("No") { _, _ -> }
            .setTitle("Delete All ToDos?")
            .setMessage("Are you sure you want to delete everything?")
            .create()

        dialog.show()
    }
}
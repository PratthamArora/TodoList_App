package com.pratthamarora.todoapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.pratthamarora.todoapp.R
import com.pratthamarora.todoapp.data.model.TodoData
import com.pratthamarora.todoapp.databinding.FragmentListBinding
import com.pratthamarora.todoapp.ui.adapter.TodoAdapter
import com.pratthamarora.todoapp.utils.SwipeToDelete
import com.pratthamarora.todoapp.utils.Utility
import com.pratthamarora.todoapp.viewmodel.SharedViewModel
import com.pratthamarora.todoapp.viewmodel.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val viewModel by viewModels<ToDoViewModel>()
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val todoAdapter by lazy { TodoAdapter(arrayListOf()) }
    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel
        setupRecyclerView()
        observeViewModel()

        setHasOptionsMenu(true)
        Utility.hideSoftKeyboard(requireActivity())

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.getAllData.observe(viewLifecycleOwner, Observer {
            sharedViewModel.checkEmptyDB(it)
            todoAdapter.setList(it)
        })

    }

    private fun setupRecyclerView() {
        binding.listRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = todoAdapter
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
            swipeToDelete(this)
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = todoAdapter.todoList[viewHolder.adapterPosition]
                viewModel.deleteData(item)
                restoreData(viewHolder.itemView, item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreData(
        view: View,
        todoData: TodoData
    ) {
        Snackbar.make(view, "Deleted ${todoData.title}", Snackbar.LENGTH_LONG).also {
            it.apply {
                setAction("Undo") {
                    viewModel.insertData(todoData)
                }
                show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as? SearchView
        searchView?.apply {
            setOnQueryTextListener(this@ListFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteAll -> deleteAllData()
            R.id.priorityHigh -> viewModel.sortByHighPriority.observe(viewLifecycleOwner, Observer {
                todoAdapter.setList(it)
            })
            R.id.priorityLow -> viewModel.sortByLowPriority.observe(viewLifecycleOwner, Observer {
                todoAdapter.setList(it)
            })
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDB(query)
        }
        return true
    }

    private fun searchDB(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchData(searchQuery).observe(viewLifecycleOwner, Observer {
            it?.let {
                todoAdapter.setList(it)
            }
        })

    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDB(query)
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
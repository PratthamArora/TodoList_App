package com.pratthamarora.todoapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratthamarora.todoapp.R
import com.pratthamarora.todoapp.ui.adapter.TodoAdapter
import com.pratthamarora.todoapp.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val viewmodel by viewModels<ToDoViewModel>()
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
        viewmodel.getAllData.observe(viewLifecycleOwner, Observer {
            todoAdapter.setList(it)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
    }
}
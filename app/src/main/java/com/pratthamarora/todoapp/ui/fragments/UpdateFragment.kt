package com.pratthamarora.todoapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.pratthamarora.todoapp.R


class UpdateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
    }

}
package com.pratthamarora.todoapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pratthamarora.todoapp.R
import com.pratthamarora.todoapp.data.model.TodoData
import com.pratthamarora.todoapp.databinding.FragmentUpdateBinding
import com.pratthamarora.todoapp.utils.Utility.hideTheKeyboard
import com.pratthamarora.todoapp.viewmodel.SharedViewModel
import com.pratthamarora.todoapp.viewmodel.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_update.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val viewModel by viewModels<ToDoViewModel>()
    private val sdf = SimpleDateFormat("MMM dd yyyy, h:mm aa", Locale.getDefault())
    private var _binding: FragmentUpdateBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args
        setHasOptionsMenu(true)

        binding.spinnerPriorityUpdate.onItemSelectedListener = sharedViewModel.listener
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_menu -> {
                updateData()
            }
            R.id.delete_menu -> {
                deleteData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteData() {
        val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteData(args.todo)
                Toast.makeText(
                    requireContext(),
                    "Successfully Deleted ${args.todo.title}",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            .setNegativeButton("No") { _, _ -> }
            .setTitle("Delete ${args.todo.title}?")
            .setMessage("Are you sure you want to delete '${args.todo.title}'?")
            .create()

        dialog.show()
    }

    private fun updateData() {
        val title = titleETUpdate.text.toString()
        val desc = descriptionETUpdate.text.toString()
        val priority = spinnerPriorityUpdate.selectedItem.toString()
        val validate = sharedViewModel.checkData(title, desc)
        val dateCreated = sdf.format(System.currentTimeMillis())
        if (validate) {
            requireContext().hideTheKeyboard(descriptionETUpdate as EditText)
            val updatedData = TodoData(
                args.todo.id,
                title,
                sharedViewModel.setPriority(priority),
                desc,
                dateCreated
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
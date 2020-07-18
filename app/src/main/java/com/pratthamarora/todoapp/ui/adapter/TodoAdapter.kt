package com.pratthamarora.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pratthamarora.todoapp.R
import com.pratthamarora.todoapp.data.model.Priority
import com.pratthamarora.todoapp.data.model.TodoData
import com.pratthamarora.todoapp.ui.fragments.ListFragmentDirections
import kotlinx.android.synthetic.main.todo_item.view.*

class TodoAdapter(private var todoList: List<TodoData>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        )
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList.asReversed()[position]
        val priority = todo.priority
        holder.itemView.apply {
            titleTV.text = todo.title
            descTV.text = todo.description
            when (priority) {
                Priority.HIGH -> priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.red
                    )
                )
                Priority.MEDIUM -> priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.yellow
                    )
                )
                Priority.LOW -> priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.green
                    )
                )
            }
            itemBG.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(
                    todo = todo
                )
                findNavController().navigate(action)
            }
        }
    }

    fun setList(list: List<TodoData>) {
        this.todoList = list
        notifyDataSetChanged()
    }
}
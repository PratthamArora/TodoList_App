package com.pratthamarora.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pratthamarora.todoapp.data.model.TodoData
import com.pratthamarora.todoapp.databinding.TodoItemBinding

class TodoAdapter(var todoList: List<TodoData>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: TodoData) {
            binding.todoData = todo
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TodoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TodoItemBinding.inflate(layoutInflater, parent, false)
                return TodoViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.from(parent)
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        holder.bind(todo)
    }

    fun setList(list: List<TodoData>) {
        val diffUtil = TodoDiffUtil(todoList, list)
        val differResult = DiffUtil.calculateDiff(diffUtil)
        this.todoList = list
        differResult.dispatchUpdatesTo(this)
    }
}
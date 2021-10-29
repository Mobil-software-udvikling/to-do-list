package com.to_do_list.app

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.to_do_list.app.databinding.TodoListBinding

class ToDoListAdapter(private val toDoLists: MutableList<ToDoList>) :
    RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holderList: ViewHolder, position: Int) {
        Log.i("test", "Test from within " + toDoLists.toString())
        with(holderList) {
            with(toDoLists[position]) {
                binding.tvColab.text = collaborators.toString()
                binding.tvListName.text = listName

            }
        }
    }

    override fun getItemCount(): Int {
        return toDoLists.size
    }

    fun addTodoList(todoList: ToDoList) {
        toDoLists.add(todoList)
        notifyItemInserted(toDoLists.size - 1)
    }


    inner class ViewHolder(val binding: TodoListBinding) : RecyclerView.ViewHolder(binding.root)
}
package com.to_do_list.app.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.to_do_list.app.common.entities.ToDoList
import com.to_do_list.app.databinding.TodoListBinding


/**
 * @author Nichlas Daniel Boraso(nibor19)
 * @author Laust Christensen(lauch19)
 */
class ToDoListAdapter(
    private val toDoLists: MutableList<ToDoList>,
    private val listClickListener: ListOnClickListener
) :
    RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holderList: ViewHolder, position: Int) {
        with(holderList) {
            with(toDoLists[position]) {
                binding.tvColab.text = collaborators.toString()
                binding.tvListName.text = listName
            }
        }
        holderList.itemView.setOnClickListener {
            listClickListener.onListClickListener(toDoLists[position])
        }

        holderList.itemView.setOnLongClickListener {
            listClickListener.onListLongClickListener(toDoLists[position])
            true
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
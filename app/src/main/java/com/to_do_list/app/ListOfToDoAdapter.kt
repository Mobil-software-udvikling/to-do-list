package com.to_do_list.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.to_do_list.app.databinding.TodoLayoutBinding

class ListOfToDoAdapter(private var todo: MutableList<ToDo>) :
    RecyclerView.Adapter<ListOfToDoAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TodoLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListOfToDoAdapter.ViewHolder {
        val binding = TodoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(todo[position]) {
                binding.etDescriptionTODO.text = description
                binding.etCompletionState.text = completionState.toString()
                binding.etPeople.text = assignedPeople

            }
        }
    }

    fun addToDo(toDo: ToDo) {
        todo.add(toDo)
        notifyItemInserted(todo.size - 1)
    }

}
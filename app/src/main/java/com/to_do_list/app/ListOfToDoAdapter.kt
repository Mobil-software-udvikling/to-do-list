package com.to_do_list.app

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.to_do_list.app.databinding.TodoLayoutBinding

class ListOfToDoAdapter(
    private var todo: MutableList<ToDo>,
    private var itemClickListener: ToDoListClickListener
) :
    RecyclerView.Adapter<ListOfToDoAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TodoLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(todo[position]) {
                binding.etDescriptionTODO.text = description
                binding.etPeople.text = assignedPeople
                when(completionState) {
                    0 -> binding.etCompletionState.background.setTint(Color.GREEN)
                    1 -> binding.etCompletionState.background.setTint(Color.YELLOW)
                    2 -> binding.etCompletionState.background.setTint(Color.RED)
                }

            }
        }
        holder.itemView.setOnClickListener {
            itemClickListener.onListClickListener(todo[position])
        }
    }

    fun addToDo(toDo: ToDo) {
        todo.add(toDo)
        notifyItemInserted(todo.size - 1)
    }

}
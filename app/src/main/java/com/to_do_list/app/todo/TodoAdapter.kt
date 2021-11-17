package com.to_do_list.app.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.to_do_list.app.R
import com.to_do_list.app.databinding.TodoListBinding

class TodoAdapter(val mainActivity: TodoListActivity, private val todoList: ArrayList<ToDo>) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holderList: ViewHolder, position: Int) {
        with(holderList) {
            with(todoList[position]) {
                binding.tvColab.text = assignedPeople
                binding.tvListName.text = description
                if (completionState == 1) {
                    binding.icon.background = mainActivity.getDrawable(R.drawable.border_green)
                } else {
                    binding.icon.background = mainActivity.getDrawable(R.drawable.border)
                }

                binding.mainLayout.setOnClickListener {
                        mainActivity.itemClicked(todoList[position])
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    inner class ViewHolder(val binding: TodoListBinding) : RecyclerView.ViewHolder(binding.root)
}
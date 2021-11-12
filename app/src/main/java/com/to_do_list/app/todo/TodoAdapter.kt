package com.to_do_list.app.todo

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.to_do_list.app.R
import com.to_do_list.app.databinding.TodoListBinding

class TodoAdapter(val mainActivity: TodoListActivity, private val todoList: MutableList<ToDo>) :
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
                    if (completionState == 0) {
                        val intent = Intent(mainActivity, TodoAddUpdateDeleteActivity::class.java)
                        intent.putExtra("Topic", mainActivity.todoTitle)
                        intent.putExtra("assignedPeople", todoList[position].assignedPeople)
                        intent.putExtra("completionState", todoList[position].completionState)
                        intent.putExtra("description", todoList[position].description)
                        mainActivity.startActivity(intent)
                    }
                }
            }
        }

    }
    override fun getItemCount(): Int {
        return todoList.size
    }
    
    inner class ViewHolder(val binding: TodoListBinding) : RecyclerView.ViewHolder(binding.root)
}
package com.to_do_list.app.topic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.to_do_list.app.databinding.TodoListBinding

class ToDoTopicAdapter(val mainActivity: MainActivity, private val toDoTopics: MutableList<ToDoTopic>) :
    RecyclerView.Adapter<ToDoTopicAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holderList: ViewHolder, position: Int) {
        with(holderList) {
            with(toDoTopics[position]) {
                binding.tvColab.text = collaborators.toString()
                binding.tvListName.text = topicName

                binding.mainLayout.setOnClickListener {
                    mainActivity.todoTopicItemClick(toDoTopics[position])
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return toDoTopics.size
    }

    fun addTodoList(todoTopic: ToDoTopic) {
        toDoTopics.add(todoTopic)
        notifyItemInserted(toDoTopics.size - 1)
    }


    inner class ViewHolder(val binding: TodoListBinding) : RecyclerView.ViewHolder(binding.root)
}
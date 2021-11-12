package com.to_do_list.app.topic

import com.to_do_list.app.todo.ToDo

data class ToDoTopic(
    val todoArrayList: ArrayList<ToDo>, var topicName: String, val collaborators: ArrayList<String>
)

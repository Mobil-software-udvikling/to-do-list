package com.to_do_list.app

data class ToDoList(
    val toDos: ArrayList<ToDo>, val listName: String, val collaborators: ArrayList<String>
)

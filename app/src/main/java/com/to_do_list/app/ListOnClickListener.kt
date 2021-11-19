package com.to_do_list.app

interface ListOnClickListener {
    fun onListClickListener(data: ToDoList)

    fun onListLongClickListener(data: ToDoList)
}
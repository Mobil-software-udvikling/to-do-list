package com.to_do_list.app.todo

import com.to_do_list.app.common.entities.ToDo

interface ToDoClickListener {
    fun onListClickListener(data: ToDo)
}
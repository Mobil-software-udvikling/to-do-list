package com.to_do_list.app

import com.to_do_list.app.common.entities.ToDo

interface ToDoListClickListener {
    fun onListClickListener(data: ToDo)
}
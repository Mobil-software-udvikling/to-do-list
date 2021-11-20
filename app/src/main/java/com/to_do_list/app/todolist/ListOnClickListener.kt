package com.to_do_list.app.todolist

import com.to_do_list.app.common.entities.ToDoList

/**
 * @author Nichlas Daniel Boraso(nibor19)
 * @author Laust Christensen(lauch19)
 */
interface ListOnClickListener {
    fun onListClickListener(data: ToDoList)
    fun onListLongClickListener(data: ToDoList)
}
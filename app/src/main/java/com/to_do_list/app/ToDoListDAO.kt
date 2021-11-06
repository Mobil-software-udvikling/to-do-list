package com.to_do_list.app

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoListDAO {
    @Query("SELECT * FROM ToDoList")
    fun getAll(): MutableList<ToDoList>

    @Insert
    fun insert(toDoList: ToDoList) : Long

    @Delete
    fun delete(toDoList: ToDoList)
}
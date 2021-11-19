package com.to_do_list.app

import androidx.room.*


/**
 * @author Nichlas Daniel Boraso(nibor19)
 * @author Laust Christensen(lauch19)
 */
@Dao
interface ToDoListDAO {

    @Query("SELECT * FROM ToDoList WHERE :id = id")
    fun getToDoList(id: Int) : ToDoList

    @Query("SELECT * FROM ToDoList")
    fun getAll(): MutableList<ToDoList>

    @Insert
    fun insert(toDoList: ToDoList): Long

    @Delete
    fun delete(toDoList: ToDoList)

    @Update
    fun update(toDoList: ToDoList)

}
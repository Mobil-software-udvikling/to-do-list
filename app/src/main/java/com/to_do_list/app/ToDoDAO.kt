package com.to_do_list.app

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoDAO {

    @Query("SELECT * FROM ToDo WHERE :listID = list")
    fun getAll(listID : Int) : MutableList<ToDo>

    @Insert
    fun insert(toDo : ToDo)

    @Delete
    fun delete(toDo : ToDo)

}
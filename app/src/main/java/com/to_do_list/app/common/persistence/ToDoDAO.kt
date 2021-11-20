package com.to_do_list.app.common.persistence

import androidx.room.*
import com.to_do_list.app.common.entities.ToDo


/**
 * @author Nichlas Daniel Boraso(nibor19)
 * @author Laust Christensen(lauch19)
 */
@Dao
interface ToDoDAO {

    @Query("SELECT * FROM ToDo WHERE :listID = list")
    fun getAll(listID : Int) : MutableList<ToDo>

    @Insert
    fun insert(toDo : ToDo)

    @Delete
    fun delete(toDo : ToDo)

    @Update
    fun updateToDo(toDo : ToDo)

}
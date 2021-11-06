package com.to_do_list.app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ToDoList(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    //val toDos: ArrayList<ToDo>,
    val listName: String,
    val collaborators: String
    )
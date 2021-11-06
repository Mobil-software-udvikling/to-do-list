package com.to_do_list.app

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "ToDo", foreignKeys = arrayOf(
    ForeignKey(
        entity = ToDoList::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("list"),
        onDelete = ForeignKey.CASCADE
    )
))
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val description: String,
    val completionState: Int = 0,
    val assignedPeople: String,
    val list : Int
)

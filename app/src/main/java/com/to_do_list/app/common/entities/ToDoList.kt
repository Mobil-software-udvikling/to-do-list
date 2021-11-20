package com.to_do_list.app.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Nichlas Daniel Boraso(nibor19)
 * @author Laust Christensen(lauch19)
 */
@Entity
data class ToDoList(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val listName: String,
    val collaborators: String
    )
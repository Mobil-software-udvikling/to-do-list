package com.to_do_list.app

import java.util.*
import kotlin.collections.ArrayList

data class ToDo(
    val description: String,
    val completionState: Int = 0,
    val assignedPeople: ArrayList<String>

)

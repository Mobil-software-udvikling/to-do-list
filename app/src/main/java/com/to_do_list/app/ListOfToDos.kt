package com.to_do_list.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListOfToDos : AppCompatActivity() {

    private var rvTodo: RecyclerView? = null

    var todoList = mutableListOf<ToDo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_to_dos)

        //Addded object to-DO from the input by the user
        val description = intent.getStringExtra("EXTRA_DESCRIPTION")
        val completionState = intent.getIntExtra("EXTRA_COMPLETIONSTATE", 0)
        val person = intent.getStringExtra("EXTRA_PEOPLE")

        val peopleList = ArrayList<String>()
        peopleList.add(person.toString())

        val todoPerson1 = ToDo(description.toString(), completionState, peopleList)

        todoList.add(todoPerson1)

        rvTodo = findViewById<RecyclerView>(R.id.rvToDos)
        rvTodo!!.layoutManager = LinearLayoutManager(this)
        rvTodo!!.adapter = ListOfToDoAdapter(todoList)


        val mAddFab: FloatingActionButton = findViewById(R.id.add_fab)

        mAddFab.setOnClickListener {
            val intent = Intent(this, AddToDO::class.java)
            startActivity(intent)
        }

    }

}
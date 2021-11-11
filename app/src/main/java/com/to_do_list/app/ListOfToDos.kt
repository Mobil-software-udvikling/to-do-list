package com.to_do_list.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ListOfToDos : AppCompatActivity() {

    val descriptionList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_to_dos)

        postList()

        val rvTodo = findViewById<RecyclerView>(R.id.rvToDos)
        rvTodo.layoutManager = LinearLayoutManager(this)
        rvTodo.adapter = ListOfToDoAdapter(descriptionList)

        //-----------------------------------------------------------------
        // Get the Intent that started this activity and extract the string
        //val listTitle = intent.getStringExtra()

        // Capture the layout's TextView and set the string as the title of the To Do List
        //val textView = findViewById<TextView>(R.id.titleOfList).apply { text = listTitle }

        val mAddFab : FloatingActionButton = findViewById(R.id.add_fab)
        /*
        mAddFab.setOnClickListener{
                view -> Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
        }
         */
        mAddFab.setOnClickListener{
            val intent = Intent(this, AddToDO::class.java)
            startActivity(intent)
        }
        //-----------------------------------------------------------------

        //Addded object to-DO from the input by the user
        val description = intent.getStringExtra("EXTRA_DESCRIPTION")
        val completionState = intent.getIntExtra("EXTRA_COMPLETIONSTATE",0)
        val person = intent.getStringExtra("EXTRA_PEOPLE")

        val peopleList = ArrayList<String>()
        peopleList.add(person.toString())

        val todoPerson1 = ToDo(description.toString(),completionState, peopleList)

        descriptionList.add(description.toString())
    }

    private fun addTotodoList(description: String){
        descriptionList.add(description)
    }

    private fun postList(){
        for (i in 1..5){
           addTotodoList("This is")
        }
    }

}
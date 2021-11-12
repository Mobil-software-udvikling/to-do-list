package com.to_do_list.app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ListOfToDos : AppCompatActivity() {

    private var rvTodo: RecyclerView? = null

    val descriptionList = mutableListOf<String>()

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == Activity.RESULT_OK){
            //Addded object to-DO from the input by the user
            val description = intent.getStringExtra("EXTRA_DESCRIPTION")
            val completionState = intent.getIntExtra("EXTRA_COMPLETIONSTATE",0)
            val person = intent.getStringExtra("EXTRA_PEOPLE")

            val peopleList = ArrayList<String>()
            peopleList.add(person.toString())

            descriptionList.add(description.toString())

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_to_dos)

        rvTodo = findViewById<RecyclerView>(R.id.rvToDos)
        rvTodo!!.layoutManager = LinearLayoutManager(this)
        rvTodo!!.adapter = ListOfToDoAdapter(descriptionList)


        val mAddFab : FloatingActionButton = findViewById(R.id.add_fab)

        mAddFab.setOnClickListener{
            val intent = Intent(this, AddToDO::class.java)
            startActivity(intent)
        }



        /*
        //Addded object to-DO from the input by the user
        val description = intent.getStringExtra("EXTRA_DESCRIPTION")
        val completionState = intent.getIntExtra("EXTRA_COMPLETIONSTATE",0)
        val person = intent.getStringExtra("EXTRA_PEOPLE")

        val peopleList = ArrayList<String>()
        peopleList.add(person.toString())

        val todoPerson1 = ToDo(description.toString(),completionState, peopleList)

        descriptionList.add(description.toString())
        */

        getResult.launch(intent)
    }

}
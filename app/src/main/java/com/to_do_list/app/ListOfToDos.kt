package com.to_do_list.app

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListOfToDos : AppCompatActivity(), View.OnClickListener {

    private var rvTodo: RecyclerView? = null
    var listOfToDo = mutableListOf<ToDo>()

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val description = it.data?.getStringExtra("EXTRA_DESCRIPTION")
            val completionState = it.data?.getIntExtra("EXTRA_COMPLETIONSTATE", 0)
            val people = it.data?.getStringExtra("EXTRA_PEOPLE")
            val colourCircle = ContextCompat.getDrawable(this, R.drawable.circle_to_do)

            if (completionState == 1) {
                if (colourCircle != null) {
                    colourCircle.setTint(Color.YELLOW)
                }
            }

            if (completionState == 2) {
                if (colourCircle != null) {
                    colourCircle.setTint(Color.RED)
                }
            }

            listOfToDo.add(ToDo(0, description!!, completionState!!, people!!, 0))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_to_dos)

        //get passed listName from intent
        val listName : String? = intent.getStringExtra("ListName")

        //get the Headers text field
        val headerText : TextView = findViewById(R.id.titleOfList)

        //Set the textFields text to the retrieved listName
        headerText.text = listName

        rvTodo = findViewById(R.id.rvToDos)
        rvTodo!!.layoutManager = LinearLayoutManager(this)
        rvTodo!!.adapter = ListOfToDoAdapter(listOfToDo)

        val button: FloatingActionButton = findViewById(R.id.add_fab)
        button.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, AddToDO::class.java)
        getResult.launch(intent)
    }

}
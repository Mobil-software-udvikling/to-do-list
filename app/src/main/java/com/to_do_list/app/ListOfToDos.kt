package com.to_do_list.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListOfToDos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_to_dos)

        // Get the Intent that started this activity and extract the string
        //val listTitle = intent.getStringExtra()

        // Capture the layout's TextView and set the string as the title of the To Do List
        //val textView = findViewById<TextView>(R.id.titleOfList).apply { text = listTitle }

        val mAddFab = findViewById<FloatingActionButton>(R.id.add_fab)
    }
}
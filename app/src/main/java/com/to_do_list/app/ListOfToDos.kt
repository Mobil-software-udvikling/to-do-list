package com.to_do_list.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ListOfToDos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_to_dos)

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
    }
}
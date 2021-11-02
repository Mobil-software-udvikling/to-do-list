package com.to_do_list.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AddToDO : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_todo_screen)


        val button: Button = findViewById(R.id.btnApply) as Button

        button.setOnClickListener {
            val description : TextView = findViewById(R.id.etDescription)
            val completionState : TextView = findViewById(R.id.etCompletionState)
            val people : TextView = findViewById(R.id.etPeople)

            Intent(this, ToDoOverview::class.java).also {
                it.putExtra("EXTRA_DESCRIPTION", description.text.toString())
                it.putExtra("EXTRA_COMPLETIONSTATE", completionState.text.toString().toInt())
                it.putExtra("EXTRA_PEOPLE", people.text.toString())
                startActivity(it)
            }
        }
    }
}
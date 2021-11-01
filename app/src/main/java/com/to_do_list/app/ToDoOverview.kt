package com.to_do_list.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ToDoOverview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_overview)

        val people = ArrayList<String>()
        people.add("John")
        people.add("Bob")

        val todoPerson = ToDo("There is none",0, people)

        val textView : TextView = findViewById<TextView>(R.id.text)


        //textView.text = getString(todoPerson.description)

    }



}
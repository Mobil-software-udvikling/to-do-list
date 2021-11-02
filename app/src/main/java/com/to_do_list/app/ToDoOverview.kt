package com.to_do_list.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ToDoOverview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_overview)

        val textView : TextView = findViewById<TextView>(R.id.etToDoText)

        val people1 = ArrayList<String>()
        people1.add("John")
        people1.add("Bob")

        val todoPerson = ToDo("There is none",0, people1)

        val description1 : String = todoPerson.description
        val completionState1 : Int = todoPerson.completionState
        val peopleList1 : ArrayList<String> = todoPerson.assignedPeople

        for (element in peopleList1){
           print(element)
        }

        //Addded object to-DO from the input by the user
        val description = intent.getStringExtra("EXTRA_DESCRIPTION")
        val completionState = intent.getIntExtra("EXTRA_COMPLETIONSTATE",0)
        val person = intent.getStringExtra("EXTRA_PEOPLE")

        val peopleList = ArrayList<String>()
        peopleList.add(person.toString())

        val todoPerson1 = ToDo(description.toString(),completionState, peopleList)

        val personString = "$todoPerson1"

        textView.text = personString


        //val toDoObject = ToDo(description.toString(), completionState, people)




        //textView.text = getString(todoPerson.description)

    }



}
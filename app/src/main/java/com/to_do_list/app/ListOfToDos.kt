package com.to_do_list.app

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListOfToDos : AppCompatActivity(), View.OnClickListener, ToDoListClickListener {

    private var rvTodo: RecyclerView? = null
    var listOfToDo = mutableListOf<ToDo>()
    var id: Int = -1
    var colorCircle: Drawable? = null

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {

        if (it.resultCode == Activity.RESULT_OK) {

            Log.d("MUTABLELISTSIZE:", listOfToDo.toString())
            if (it.data?.hasExtra("ADD_EXTRA_INTENT")!!) {
                Log.d("ADDTODO", "AM I HERE?")
                val description = it.data?.getStringExtra("EXTRA_DESCRIPTION")
                val completionState = it.data?.getIntExtra("EXTRA_COMPLETIONSTATE", 0)
                val people = it.data?.getStringExtra("EXTRA_PEOPLE")

                colorCircle = ContextCompat.getDrawable(this, R.drawable.circle_to_do)

                if (completionState == 1) {
                    if (colorCircle != null) {
                        colorCircle!!.setTint(Color.YELLOW)
                    }
                }

                if (completionState == 2) {
                    if (colorCircle != null) {
                        colorCircle!!.setTint(Color.RED)
                    }
                }

                if (completionState == 0) {
                    if (colorCircle != null) {
                        colorCircle!!.setTint(Color.GREEN)
                    }
                }
                id++

                if (listOfToDo.isNotEmpty()) {
                    id = listOfToDo.size - 1

                }

                listOfToDo.add(ToDo(id, description!!, completionState!!, people!!))
                Log.d("MUTUABLELISTINDEX0", listOfToDo.toString())
            }
        }
    }

    private val getResultUpdateDelete = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {

        if (it.resultCode == Activity.RESULT_OK) {


            if (it.data?.hasExtra("todoModelDelete")!!) {
                val updateID = it.data?.getIntExtra("update_ID", 0)
                listOfToDo.removeAt(updateID!!)

                if (listOfToDo.isNotEmpty()) {
                    for (todo: ToDo in listOfToDo) {
                        if (todo.todoId > updateID) {
                            todo.todoId = todo.todoId - 1
                        }
                    }
                }
                rvTodo!!.adapter!!.notifyDataSetChanged()
                Log.d("MUTUABLELISTINDEX", listOfToDo.toString())
            }

            if (it.data?.hasExtra("UPDATEtodo")!!) {

                //Update
                val updateDescription = it.data?.getStringExtra("update_Decsription")
                val updatePeople = it.data?.getStringExtra("update_People")
                val updateCompleitonState = it.data?.getIntExtra("update_CompleitonState", 0)
                val updateID = it.data?.getIntExtra("update_ID", 0)

                if (updateCompleitonState == 1) {
                    if (colorCircle != null) {
                        colorCircle!!.setTint(Color.YELLOW)
                    }
                }

                if (updateCompleitonState == 2) {
                    if (colorCircle != null) {
                        colorCircle!!.setTint(Color.RED)
                    }
                }

                if (updateCompleitonState == 0) {
                    if (colorCircle != null) {
                        colorCircle!!.setTint(Color.GREEN)
                    }
                }

                listOfToDo[updateID!!].description = updateDescription!!
                listOfToDo[updateID!!].assignedPeople = updatePeople!!
                listOfToDo[updateID!!].completionState = updateCompleitonState!!

                //listOfToDo.set(updateID!!, ToDo(updateID!!, updateDescription!!, updateCompleitonState!!, updatePeople!!))

                rvTodo!!.layoutManager!!.removeAllViews()
                rvTodo!!.layoutManager = LinearLayoutManager(this)
                rvTodo!!.adapter = ListOfToDoAdapter(listOfToDo, this)
                rvTodo!!.recycledViewPool.clear()

                rvTodo!!.adapter!!.notifyDataSetChanged()
                Log.d("MUTUABLELISTINDEX", listOfToDo.toString())

                intent.putExtra("RefreashUpdate", "Refreash")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_to_dos)

        //get passed listName from intent
        val listName: String? = intent.getStringExtra("ListName")

        //get the Headers text field
        val headerText: TextView = findViewById(R.id.titleOfList)

        //Set the textFields text to the retrieved listName
        headerText.text = listName

        rvTodo = findViewById(R.id.rvToDos)
        rvTodo!!.layoutManager = LinearLayoutManager(this)
        rvTodo!!.adapter = ListOfToDoAdapter(listOfToDo, this)

        val button: FloatingActionButton = findViewById(R.id.add_fab)
        button.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, AddToDO::class.java)
        getResult.launch(intent)
    }

    override fun onListClickListener(data: ToDo) {
        val intent = Intent(this, TodoAddUpdateDeleteActivity::class.java)
        intent.putExtra("todoModel", data)
        //TODO put extra intent with the clicked lists ID(Missing in dataclass and comes with merge)
        getResultUpdateDelete.launch(intent)
    }


}
package com.to_do_list.app.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.to_do_list.app.R
import com.to_do_list.app.common.entities.ToDo
import com.to_do_list.app.common.persistence.ToDoListDatabase

class ToDoOverviewActivity : AppCompatActivity(), View.OnClickListener, ToDoClickListener {

    private var rvTodo: RecyclerView? = null
    var listOfToDo = mutableListOf<ToDo>()
    var id: Int = -1

    private lateinit var toDoListDatabase: ToDoListDatabase

    private var loadThread: LoadThread = LoadThread()


    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {

        if (it.resultCode == Activity.RESULT_OK) {
            if (it.data?.hasExtra("ADD_EXTRA_INTENT")!!) {
                val description = it.data?.getStringExtra("EXTRA_DESCRIPTION")
                val completionState = it.data?.getIntExtra("EXTRA_COMPLETIONSTATE", 0)
                val people = it.data?.getStringExtra("EXTRA_PEOPLE")

                val listId = intent.getIntExtra("ListID", -1)

                if (listId != -1) {
                    val addToDoThread =
                        AddTodoThread(ToDo(0, description!!, completionState!!, people!!, listId))
                    addToDoThread.start()
                }
            }
        }
    }

    private val getResultUpdateDelete = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {

        if (it.resultCode == Activity.RESULT_OK) {

            if (it.data?.hasExtra("todoModelDelete")!!) {

                val deleteDescription = it.data?.getStringExtra("delete_Decsription")
                val deletePeople = it.data?.getStringExtra("delete_People")
                val deleteCompleitonState = it.data?.getIntExtra("delete_CompleitonState", 0)
                val todoId = it.data?.getIntExtra("delete_ID", -1)
                val listID = it.data?.getIntExtra("ListID", -1)

                if (listID != -1 || todoId != -1) {
                    val deleteToDoThread = DeleteToDoThread(
                        ToDo(
                            todoId!!,
                            deleteDescription!!,
                            deleteCompleitonState!!,
                            deletePeople!!,
                            listID!!
                        )
                    )
                    deleteToDoThread.start()
                }

                rvTodo!!.adapter!!.notifyDataSetChanged()
            }

            if (it.data?.hasExtra("UPDATEtodo")!!) {

                //Update
                val updateDescription = it.data?.getStringExtra("update_Decsription")
                val updatePeople = it.data?.getStringExtra("update_People")
                val updateCompleitonState = it.data?.getIntExtra("update_CompleitonState", 1)
                val updateID = it.data?.getIntExtra("update_ID", 0)
                val listID = it.data?.getIntExtra("ListID", -1)


                var position = -1
                for (i in toDoListDatabase.toDoDao().getAll(listID!!).indices) {
                    if (toDoListDatabase.toDoDao().getAll(listID)[i].id == updateID) {
                        position = i
                    }
                }

                if (listID != -1) {
                    val updateToDoThread = UpdateToDoThread(
                        ToDo(
                            updateID!!,
                            updateDescription!!,
                            updateCompleitonState!!,
                            updatePeople!!,
                            listID
                        )
                    )
                    updateToDoThread.start()
                }
                if (position != -1) {
                    rvTodo!!.adapter!!.notifyItemChanged(position)
                }

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
        rvTodo!!.adapter = ToDoAdapter(listOfToDo, this)

        val button: FloatingActionButton = findViewById(R.id.add_fab)
        button.setOnClickListener(this)

        //Room
        toDoListDatabase = ToDoListDatabase.getAppDatabse(this)!!

        //start the loadThread to load from database in seperate thread
        loadThread.start()
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, AddToDoActivity::class.java)
        getResult.launch(intent)
    }

    //When app is resumed, reload the data to be sure we always have the newest updates from database
    override fun onResume() {
        listOfToDo.clear()
        Thread.sleep(200)
        startLoadThread()
        super.onResume()
    }

    fun loadListsFromDatabase() {
        //Clear all the data from the list
        listOfToDo.clear()

        //Get all list from databse and add them to the list
        val listId = intent.getIntExtra("ListID", -1)
        if (listId != -1) {
            listOfToDo.addAll(
                toDoListDatabase.toDoDao().getAll(intent.getIntExtra("ListID", listId))
            )
        }
    }

    //therad for handling loading from database
    inner class LoadThread : Thread() {
        override fun run() {
            if (!currentThread().isInterrupted) {
                Log.i("db", "Database thread started")
                loadListsFromDatabase()
            }
        }
    }

    override fun onListClickListener(data: ToDo) {
        val intent = Intent(this, TodoAddUpdateDeleteActivity::class.java)
        intent.putExtra("todoModel", data)
        intent.putExtra("toDoId", data.id)
        intent.putExtra("ListID", data.list)
        getResultUpdateDelete.launch(intent)
    }

    inner class UpdateToDoThread(private val toDo: ToDo?) : Thread() {
        override fun run() {
            //check if the parsed ToDoList is not null in order to save it(we cannot save null)
            if (toDo != null || toDo?.list != -1) {
                toDoListDatabase.toDoDao().updateToDo(toDo!!)
            }
        }
    }

    //Thread for handling the insert and afterwards reload the ToDoLists shown in the RecyclerView
    inner class AddTodoThread(private val toDo: ToDo?) : Thread() {
        override fun run() {
            //We can only insert if the parsed data is not null
            if (toDo != null || toDo?.list != -1) {
                toDoListDatabase.toDoDao().insert(toDo!!)
            }
        }
    }

    inner class DeleteToDoThread(private val toDo: ToDo?) : Thread() {
        override fun run() {
            if (toDo != null || toDo?.list != -1) {
                toDoListDatabase.toDoDao().delete(toDo!!)
            }
        }
    }

    private fun startLoadThread() {
        if (!loadThread.isInterrupted) {
            loadThread.interrupt()
        }
        loadThread = LoadThread()
        loadThread.start()
    }

}
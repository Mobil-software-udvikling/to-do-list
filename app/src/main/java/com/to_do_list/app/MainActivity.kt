package com.to_do_list.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Nichlas Daniel Boraso(nibor19)
 * @author Laust Christensen(lauch19)
 */
class MainActivity : AppCompatActivity(), View.OnClickListener, ListOnClickListener {

    private var rView: RecyclerView? = null

    private var toDoList: MutableList<ToDoList> = ArrayList()
    private lateinit var toDoListAdapter: ToDoListAdapter

    private lateinit var toDoListDatabase: TodoListDatabse

    private var loadThread: LoadThread = LoadThread()

    //Listens for added results from other activites
    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        //Only if the result code is ok, we add the list to ToDoLists
        if (it.resultCode == Activity.RESULT_OK) {
            //Get the data from intent
            val value = it.data?.getStringExtra("ListName")
            val newList = ToDoList(0, value!!, "")

            //Instatiate new thread for inserting the added list to the database and reload the data from database
            val addListThread = AddListAndReloadThread(newList)
            addListThread.start()
        }
    }

    private val getResultFromEditUpdate = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            //extraxt the changed listID
            val ID = it.data?.getIntExtra("ListID", -1)
            if (it.data?.hasExtra("update")!!) {
                if (ID == -1) {
                    Log.i("updateError", "Something went wrong with the update..")
                } else {
                    //Extract the name
                    val newName = it.data?.getStringExtra("UpdatedListName")
                    val updatedToDoList = ToDoList(ID!!, newName!!, "")

                    //Start a new update and reload thread
                    val updateListThread = UpdateListAndReloadThread(updatedToDoList)
                    updateListThread.start()
                }

            } else if (it.data?.hasExtra("delete")!!) {
                if (ID == -1) {
                    Log.i("DeleteError", "Something went wrong in the deletion")
                } else {
                    //extract the name of the list to delete
                    val deleteName = it.data?.getStringExtra("DeleteListName")
                    val deleteToDoList = ToDoList(ID!!, deleteName!!, "")
                    //Start new deleteAndReloadThread
                    val deleteListThread = DeleteAndReloadThread(deleteToDoList)
                    deleteListThread.start()
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        val drawer = findViewById<DrawerLayout>(R.id.drawer)
        val drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toDoListAdapter = ToDoListAdapter(toDoList, this)

        rView = findViewById(R.id.rv_list)
        rView!!.adapter = toDoListAdapter
        var layoutManager = LinearLayoutManager(this)
        rView!!.layoutManager = layoutManager

        //Set this class as clickListener
        val button: View = findViewById(R.id.floatingActionButton)
        button.setOnClickListener(this)

        //Room
        toDoListDatabase = TodoListDatabse.getAppDatabse(this)!!

        //start the loadThread to load from database in seperate thread
        loadThread.start()
    }

    //Handle click on the "burger" icon and open drawer accordingly.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            android.R.id.home -> {
                val drawer: DrawerLayout = findViewById(R.id.drawer)
                drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Handles when the floating button to add new ToDoList is clicked
    override fun onClick(p0: View?) {
        //Create a new intent to handle data to and from activities
        val intent = Intent(this, AddToDoLIstActivity::class.java)
        getResult.launch(intent)
    }

    //When app is resumed, reload the data to be sure we always have the newest updates from database
    override fun onResume() {
        toDoList.clear()
        Thread.sleep(200)
        startLoadThread()
        //Notify RecyclerView about dataUpdates
        runOnUiThread(Runnable { rView?.adapter?.notifyDataSetChanged() })
        super.onResume()
    }

    fun loadListsFromDatabase() {
        //Clear all the data from the list
        toDoList.clear()

        //Get all list from databse and add them to the list
        toDoList.addAll(toDoListDatabase.ToDoListDao().getAll())
    }

    override fun onListClickListener(data: ToDoList) {
        val intent = Intent(this, ListOfToDos::class.java)
        intent.putExtra("ListName", data.listName)
        intent.putExtra("ListID", data.id)
        startActivity(intent)
    }

    override fun onListLongClickListener(data: ToDoList) {
        val intent = Intent(this, EditDeleteToDoListActivity::class.java)
        intent.putExtra("ListName", data.listName)
        intent.putExtra("ListID", data.id)
        getResultFromEditUpdate.launch(intent)
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

    inner class UpdateListAndReloadThread(private val toDoList: ToDoList?) : Thread() {
        override fun run() {
            //check if the parsed ToDoList is not null in order to save it(we cannot save null)
            if (toDoList != null) {
                toDoListDatabase.ToDoListDao().update(toDoList)
            }
        }
    }

    //Thread for handling the insert and afterwards reload the ToDoLists shown in the RecyclerView
    inner class AddListAndReloadThread(private val toDoList: ToDoList?) : Thread() {
        override fun run() {
            //We can only insert if the parsed data is not null
            if (toDoList != null) {
                toDoListDatabase.ToDoListDao().insert(toDoList)
            }
        }
    }

    inner class DeleteAndReloadThread(private val toDoList: ToDoList?) : Thread() {
        override fun run() {
            if (toDoList != null) {
                toDoListDatabase.ToDoListDao().delete(toDoList)
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
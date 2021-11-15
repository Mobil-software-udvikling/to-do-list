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

//TODO: Make comments
/**
 * @author Nichlas Daniel Boraso(nibor19)
 * @author Laust Christensen(lauch19)
 */
class MainActivity : AppCompatActivity(), View.OnClickListener, ListOnClickListener {

    private var rView: RecyclerView? = null

    private  var toDoList: MutableList<ToDoList> = ArrayList()
    private lateinit var toDoListAdapter: ToDoListAdapter

    private lateinit var toDoListDatabase : TodoListDatabse

    private var loadThread : LoadThread = LoadThread()

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
            val addListThread  = AddListAndReloadThread(newList)
            addListThread.start()
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

        //Load saved lists from databse
        //start the loadThread to load from database in seperate thread
        Log.i("ThreadStatus", loadThread.isAlive.toString())
        loadThread.start()
    }

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

    fun loadListsFromDatabase(){
        //Clear all the data from the list
        toDoList.clear()
        //Get all list from databse and add them to the list
        toDoList.addAll(toDoListDatabase.ToDoListDao().getAll())
    }
    override fun onListClickListener(data: ToDoList) {
        val intent = Intent(this, ListOfToDos::class.java)
        intent.putExtra("ListName", data.listName)
        //TODO put extra intent with the clicked lists ID(Missing in dataclass and comes with merge) 
        startActivity(intent)
    }

    //therad for handling loading from database
    inner class LoadThread : Thread() {
        override fun run() {
            Log.i("db", "Database thread started")
            loadListsFromDatabase()
        }
    }
    //Thread for handling the insert and afterwards reload the ToDoLists shown in the RecyclerView
    inner class AddListAndReloadThread(private val toDoList : ToDoList?) : Thread() {
        override fun run(){
            //We can only insert if the parsed data is not null
            if (toDoList != null) {
                toDoListDatabase.ToDoListDao().insert(toDoList)
            }
            //Reload the data by starting a new thread to load from database
            loadThread = LoadThread()
            loadThread.start()
        }
    }

}
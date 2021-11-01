package com.to_do_list.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var rView: RecyclerView? = null

    private  var toDoList: MutableList<ToDoList> = ArrayList()
    private lateinit var toDoListAdapter: ToDoListAdapter


    //Listens for added results from other activites
    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        //Only if the result code is ok, we add the list to ToDoLists
        if (it.resultCode == Activity.RESULT_OK) {
            val value = it.data?.getStringExtra("ListName")
            toDoList.add(ToDoList(ArrayList(), value!!, ArrayList()))
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
        
        toDoListAdapter = ToDoListAdapter(toDoList)

        rView = findViewById(R.id.rv_list)
        rView!!.adapter = toDoListAdapter
        var layoutManager = LinearLayoutManager(this)
        rView!!.layoutManager = layoutManager

        //Set this class as clickListener
        val button: View = findViewById(R.id.floatingActionButton)
        button.setOnClickListener(this)

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
}
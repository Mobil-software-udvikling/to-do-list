package com.to_do_list.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import android.provider.DocumentsContract
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.to_do_list.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var rView : RecyclerView? = null

    private lateinit var toDoList: MutableList<ToDoList>
    private lateinit var toDoListAdapter: ToDoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val drawer = findViewById<DrawerLayout>(R.id.drawer)
        val drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadList()

        toDoListAdapter = ToDoListAdapter(toDoList)

        rView = binding.rvList
        rView!!.adapter = toDoListAdapter
        var layoutManager = LinearLayoutManager(this)
        rView!!.layoutManager = layoutManager

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

    private fun loadList() {
        toDoList = listOf(
            ToDoList(ArrayList(), "List 1", ArrayList()),
            ToDoList(ArrayList(), "List 2", ArrayList()),
        ) as MutableList<ToDoList>
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
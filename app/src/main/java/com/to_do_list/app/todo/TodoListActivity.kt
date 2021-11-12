package com.to_do_list.app.todo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.to_do_list.app.R
import com.to_do_list.app.TodoArrayClass


class TodoListActivity : AppCompatActivity(), View.OnClickListener {

    var todoTitle: String? = ""
    private var rView: RecyclerView? = null

    private var toDoList: MutableList<ToDo> = ArrayList()
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        todoTitle = intent.getStringExtra("Title")
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        if (!todoTitle!!.isEmpty())
            toolbar.setTitle(todoTitle + " List")
        setSupportActionBar(toolbar)

        todoAdapter = TodoAdapter(this, toDoList)

        rView = findViewById(R.id.rv_list)
        rView!!.adapter = todoAdapter
        var layoutManager = LinearLayoutManager(this)
        rView!!.layoutManager = layoutManager

        //Set this class as clickListener
        val button: View = findViewById(R.id.floatingActionButton)
        button.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
        toDoList.clear()
        for (todoTopic in TodoArrayClass.getTodoTopicList())
        {
            if(todoTopic.topicName.equals(todoTitle)){
                toDoList.addAll(todoTopic.todoArrayList)
                break
            }
        }
        if (rView!!.adapter != null)
            rView!!.adapter!!.notifyDataSetChanged()

    }


    //Handles when the floating button to add new ToDoList is clicked
    override fun onClick(p0: View?) {
        //Create a new intent to handle data to and from activities
        val intent = Intent(this, TodoAddUpdateDeleteActivity::class.java)
        intent.putExtra("Topic",todoTitle)
        startActivity(intent)
    }
}
package com.to_do_list.app.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.to_do_list.app.R
import com.to_do_list.app.topic.ToDoTopic


class TodoListActivity : AppCompatActivity(), View.OnClickListener {

    private var todoTopic: ToDoTopic? = null
    private var rView: RecyclerView? = null

    private var toDoList = ArrayList<ToDo>()
    private lateinit var todoAdapter: TodoAdapter

    //Listens for added results from other activites
    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        //Only if the result code is ok, we add the list to ToDoLists
        if (it.resultCode == Activity.RESULT_OK) {
            if (it.data?.hasExtra("todoModel")!!) {
                val value = it.data?.getParcelableExtra<ToDo>("todoModel")
                toDoList.add(value!!)
            } else if (it.data?.hasExtra("todoModelUpdate")!!) {
                val value = it.data?.getParcelableExtra<ToDo>("todoModelUpdate")
                for (j in 0..toDoList.size - 1) {
                    if (toDoList.get(j).todoId==(value!!.todoId)) {
                        val oldTodo = toDoList.get(j)
                        toDoList.remove(oldTodo)
                        toDoList.add(value)
                        break
                    }
                }
            }else if (it.data?.hasExtra("todoModelDelete")!!) {
                val value = it.data?.getParcelableExtra<ToDo>("todoModelDelete")
                for (j in 0..toDoList.size - 1) {
                    if (toDoList.get(j).description.equals(value!!.description)) {
                        val oldTodo = toDoList.get(j)
                        toDoList.remove(oldTodo)
                        break
                    }
                }
            }
            rView!!.adapter!!.notifyDataSetChanged()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        todoTopic = intent.getParcelableExtra<ToDoTopic>("topicModel")
        val tempTodoList = intent.getParcelableArrayListExtra<ToDo>("todoList")
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        if (todoTopic != null) {
            toolbar.setTitle(todoTopic!!.topicName + " List")
            toDoList.addAll(tempTodoList!!)
        }
        setSupportActionBar(toolbar)

        todoAdapter = TodoAdapter(this, toDoList)

        rView = findViewById(R.id.rv_list)
        rView!!.adapter = todoAdapter
        val layoutManager = LinearLayoutManager(this)
        rView!!.layoutManager = layoutManager

        //Set this class as clickListener
        val button: View = findViewById(R.id.floatingActionButton)
        button.setOnClickListener(this)

    }

    override fun onBackPressed() {
        todoTopic!!.todoArrayList.addAll(toDoList)
        intent.putParcelableArrayListExtra("todoList", toDoList)
        intent.putExtra("topicName", todoTopic!!.topicName)
        //Set the result as OK to invoke it in mainActivity
        setResult(RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }


    //Handles when the floating button to add new ToDoList is clicked
    override fun onClick(p0: View?) {
        //Create a new intent to handle data to and from activities
        val intent = Intent(this, TodoAddUpdateDeleteActivity::class.java)
        if(toDoList.size>0){
            val templist = toDoList.sortedWith(compareBy({ it.todoId }))
            intent.putExtra("todoLastInsertedId", templist.last().todoId)
        }else{
            intent.putExtra("todoLastInsertedId", 0)
        }
        getResult.launch(intent)
    }

    fun itemClicked(todo: ToDo) {
        val intent = Intent(this, TodoAddUpdateDeleteActivity::class.java)
        intent.putExtra("todoModel", todo)
        getResult.launch(intent)
    }
}
package com.to_do_list.app.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.to_do_list.app.R
import com.to_do_list.app.TodoArrayClass

class TodoAddUpdateDeleteActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var deleteButton: Button
    private lateinit var updateButton: Button
    private lateinit var saveButton: Button
    private var assignedPeople: String = ""
    private var completionState: Int = 0
    private lateinit var description: String
    private var topic: String? = ""
    private lateinit var cbDone: CheckBox
    private lateinit var etPeople: EditText
    private lateinit var etTodoText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_add_update_delete)

        if (intent.hasExtra("Topic"))
            topic = intent.getStringExtra("Topic")
        if (intent.hasExtra("description"))
            description = intent.getStringExtra("description")!!
        if (intent.hasExtra("completionState"))
            completionState = intent.getIntExtra("completionState", 0)
        if (intent.hasExtra("assignedPeople"))
            assignedPeople = intent.getStringExtra("assignedPeople")!!

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle("Add Update Todo")

        etTodoText = findViewById(R.id.etTodoText) as EditText
        etPeople = findViewById(R.id.etPeople) as EditText
        cbDone = findViewById(R.id.cbDone) as CheckBox
        saveButton = findViewById(R.id.btnSave)
        saveButton.setOnClickListener(this)
        updateButton = findViewById(R.id.btnUpdate)
        updateButton.setOnClickListener(this)
        deleteButton = findViewById(R.id.btnDelete)
        deleteButton.setOnClickListener(this)

        if (intent.hasExtra("description")) {
            etTodoText.setText(description)
            etPeople.setText(assignedPeople)
            cbDone.isChecked = if (completionState == 1) true else false
            etPeople.isClickable= true
            etPeople.isEnabled = true
            etTodoText.isClickable = true
            etTodoText.isEnabled = true
            saveButton.isClickable = true
            saveButton.isEnabled = true
        } else {
            updateButton.isClickable = true
            updateButton.isEnabled = true
            deleteButton.isClickable = true
            deleteButton.isEnabled = true
        }


    }

    //Method for handling clicks on add new List view
    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btnSave) {
            val description = etTodoText.text.toString()
            val people = etPeople.text.toString()
            val isDone = if (cbDone.isChecked) 1 else 0
            val todo = ToDo(description, isDone, people)
            for (i in 0..TodoArrayClass.getTodoTopicList().size - 1) {
                if (TodoArrayClass.getTodoTopicList().get(i).topicName.equals(topic)) {
                    val todoTopic = TodoArrayClass.getTodoTopicList().get(i)
                    TodoArrayClass.getTodoTopicList().remove(todoTopic)
                    todoTopic.todoArrayList.add(todo)
                    TodoArrayClass.getTodoTopicList().add(todoTopic)
                    break
                }
            }
            finish()
        } else if (p0.id == R.id.btnUpdate) {
            val description = etTodoText.text.toString()
            val people = etPeople.text.toString()
            val isDone = if (cbDone.isChecked) 1 else 0
            val todo = ToDo(description, isDone, people)
            for (i in 0..TodoArrayClass.getTodoTopicList().size - 1) {
                if (TodoArrayClass.getTodoTopicList().get(i).topicName.equals(topic)) {
                    val todoTopic = TodoArrayClass.getTodoTopicList().get(i)
                    TodoArrayClass.getTodoTopicList().remove(todoTopic)

                    for (j in 0..todoTopic.todoArrayList.size - 1) {
                        if (todoTopic.todoArrayList.get(j).description.equals(description)) {
                            val oldTodo = todoTopic.todoArrayList.get(j)
                            todoTopic.todoArrayList.remove(oldTodo)
                            todoTopic.todoArrayList.add(todo)
                            break
                        }
                    }
                    TodoArrayClass.getTodoTopicList().add(todoTopic)
                    break
                }
            }
            finish()
        }else if (p0.id == R.id.btnDelete) {
            val description = etTodoText.text.toString()
            val people = etPeople.text.toString()
            val isDone = if (cbDone.isChecked) 1 else 0
            val todo = ToDo(description, isDone, people)
            for (i in 0..TodoArrayClass.getTodoTopicList().size - 1) {
                if (TodoArrayClass.getTodoTopicList().get(i).topicName.equals(topic)) {
                    val todoTopic = TodoArrayClass.getTodoTopicList().get(i)
                    TodoArrayClass.getTodoTopicList().remove(todoTopic)

                    for (j in 0..todoTopic.todoArrayList.size - 1) {
                        if (todoTopic.todoArrayList.get(j).description.equals(description)) {
                            val oldTodo = todoTopic.todoArrayList.get(j)
                            todoTopic.todoArrayList.remove(oldTodo)
                            break
                        }
                    }
                    TodoArrayClass.getTodoTopicList().add(todoTopic)
                    break
                }
            }
            finish()
        }
    }
}
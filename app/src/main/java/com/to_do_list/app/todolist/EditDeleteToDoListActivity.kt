package com.to_do_list.app.todolist

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.to_do_list.app.R

/**
 * @author Nichlas Daniel Boraso(nibor19)
 * @author Laust Christensen(lauch19)
 */
class EditDeleteToDoListActivity : AppCompatActivity() {

    private lateinit var clickedListName : String
    private lateinit var textInput : TextInputEditText
    private  var clickedListID : Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_delete_list_view)

        clickedListName = intent.getStringExtra("ListName")!!
        textInput = findViewById(R.id.listNameFieldEditDelete)
        textInput.setText(clickedListName)
        clickedListID = intent.getIntExtra("ListID", -1)

        val updateButton: Button = findViewById(R.id.UpdateButton)
        updateButton.setOnClickListener {
            setDataAndResult("update")
            finish()
        }

        val deleteButton: Button = findViewById(R.id.deleteButton)
        deleteButton.setOnClickListener {
            setDataAndResult("delete")
            finish()
        }
    }
    //sets the result of the activity when one of the buttons are clicked.
    //Takes a String to determine which action the reciever of the result should take
    private fun setDataAndResult(action : String) {
        val name: String = textInput.text.toString()
        intent.putExtra("ListName", name)
        intent.putExtra("ListID", clickedListID)
        intent.putExtra("action", action)
        setResult(RESULT_OK, intent)
    }
}
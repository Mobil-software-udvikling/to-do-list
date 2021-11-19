package com.to_do_list.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EditDeleteToDoListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_delete_list_view)

        val clickedListName : String? = intent.getStringExtra("ListName")
        val textInput : TextInputEditText = findViewById(R.id.listNameFieldEditDelete)
        textInput.setText(clickedListName)

        //TODO extract extra with the lists clicked ID

        val updateButton : Button = findViewById(R.id.UpdateButton)
        updateButton.setOnClickListener {
            //Add extra named "update" so mainActivity can react to it
            intent.putExtra("update", true)
            //Extract the updated name
            val name : String = textInput.text.toString()
            intent.putExtra("UpdatedListName", name)
            //TODO: add extra with the id so mainActivity know which list to update
            setResult(RESULT_OK, intent)
            finish()
        }

        val deleteButton : Button = findViewById(R.id.deleteButton)
        deleteButton.setOnClickListener {
            //TODO: add extra with the id so mainActivity know which list to delete
            //Add extra named "delete" so mainActivity can react to it
            intent.putExtra("delete", true)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}
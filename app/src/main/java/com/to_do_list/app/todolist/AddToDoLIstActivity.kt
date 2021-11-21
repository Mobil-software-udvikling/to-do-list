package com.to_do_list.app.todolist

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.to_do_list.app.R

/**
 * @author Nichlas Daniel Boraso(nibor19)
 * @author Laust Christensen(lauch19)
 */
class AddToDoLIstActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_list_view)
        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener(this)
    }

    //Method for handling clicks on add new List view
    override fun onClick(p0: View?) {
        //Get the input field
        val listName: TextInputEditText = findViewById(R.id.listNameField)
        //extract the inputed text
        val name: String = listName.text.toString()
        //Put the extracted name in the intent
        intent.putExtra("ListName", name)
        //Set the result as OK to invoke it in mainActivity
        setResult(RESULT_OK, intent)
        finish()
    }
}
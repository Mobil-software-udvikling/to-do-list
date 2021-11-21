package com.to_do_list.app.todo

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.to_do_list.app.R

class AddToDoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_todo_screen)

        val radioGroup: RadioGroup = findViewById(R.id.ad_radioGROUP)

        val button: Button = findViewById(R.id.btnApply)

        button.setOnClickListener {

            val description: EditText = findViewById(R.id.ad_Description)
            val people: EditText = findViewById(R.id.ad_People)
            var completionState = 0
            val radioId = radioGroup.checkedRadioButtonId

            if (radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(
                    this,
                    "Please select a option from the buttons displayed",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val radioButton: RadioButton = findViewById(radioId)
                when (radioButton.text) {
                    "To do" -> completionState = 2
                    "Doing" -> completionState = 1
                    "Done" -> completionState = 0
                }
                if (description.text.toString().equals("")) {
                    description.error = "This field can not be blank"
                } else if (people.text.toString().equals("")) {
                    people.error = "This field can not be blank"
                } else {
                    intent.putExtra("EXTRA_DESCRIPTION", description.text.toString())
                    intent.putExtra("EXTRA_COMPLETIONSTATE", completionState)
                    intent.putExtra("EXTRA_PEOPLE", people.text.toString())
                    intent.putExtra("ADD_EXTRA_INTENT", "ADDTODO")
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }
}
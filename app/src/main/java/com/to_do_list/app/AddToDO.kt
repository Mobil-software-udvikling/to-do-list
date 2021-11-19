package com.to_do_list.app

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddToDO : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_todo_screen)

        val radioGroup: RadioGroup = findViewById(R.id.ad_radioGROUP)

        val button: Button = findViewById(R.id.btnApply) as Button

        button.setOnClickListener {

            val description: EditText = findViewById(R.id.ad_Description)
            val people: EditText = findViewById(R.id.ad_People)

            var completionState: Int = 0
            val radioId = radioGroup.checkedRadioButtonId

            if (radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(
                    this,
                    "Please select a option from the buttons displayed",
                    Toast.LENGTH_SHORT
                ).show();
            } else {
                val radioButton: RadioButton = findViewById(radioId)
                if (radioButton.text.equals("Urgent")) {
                    completionState = 2
                } else if (radioButton.text.equals("Soon")) {
                    completionState = 1
                } else if (radioButton.text.equals("Completeted")) {
                    completionState = 0
                }
                if (description.text.toString().equals("")) {
                    description.setError("This field can not be blank")
                } else if (people.text.toString().equals("")) {
                    people.setError("This field can not be blank")
                } else {
                    intent.putExtra("EXTRA_DESCRIPTION", description.text.toString())
                    intent.putExtra("EXTRA_COMPLETIONSTATE", completionState)
                    intent.putExtra("EXTRA_PEOPLE", people.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }


        }


    }
}
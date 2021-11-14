package com.to_do_list.app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddToDO : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_todo_screen)


        val button: Button = findViewById(R.id.btnApply) as Button

        button.setOnClickListener {

            val description: EditText = findViewById(R.id.ad_Description)
            val completionState: EditText = findViewById(R.id.ad_CompletionState)
            val people: EditText = findViewById(R.id.ad_People)

            if (description.text.toString().equals("")) {
                description.setError("This field can not be blank")
            } else if (completionState.text.toString().equals("") || completionState.text.toString().equals("""([A-z])\w*""".toRegex())) {
                description.setError("This field can not be blank")
            } else if (people.text.toString().equals("")) {
                people.setError("This field can not be blank")
            } else {
                intent.putExtra("EXTRA_DESCRIPTION", description.text.toString())
                intent.putExtra("EXTRA_COMPLETIONSTATE", completionState.text.toString().toInt())
                intent.putExtra("EXTRA_PEOPLE", people.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}
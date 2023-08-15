package com.example.gbtest08

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SetupWizardActivity : AppCompatActivity() {
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var makeEditText: EditText
    private lateinit var modelEditText: EditText
    private lateinit var nextButton: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_wizard)

        firstNameEditText = findViewById(R.id.editTextFirstName)
        lastNameEditText = findViewById(R.id.editTextLastName)
        makeEditText = findViewById(R.id.editTextMake)
        modelEditText = findViewById(R.id.editTextModel)
        nextButton = findViewById(R.id.buttonNext)

        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)

        nextButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val make = makeEditText.text.toString()
            val model = modelEditText.text.toString()

            // Save user inputs to shared preferences
            val editor = sharedPreferences.edit()
            editor.putString("firstName", firstName)
            editor.putString("lastName", lastName)
            editor.putString("make", make)
            editor.putString("model", model)
            editor.apply()

            // Move to the next screen
            val selectedGigAppsId = 1 // Replace with your own logic to get selectedGigAppsId
            val intent = Intent(this@SetupWizardActivity, SetWiz2Act::class.java).apply {
                putExtra("selectedGigAppsId", selectedGigAppsId)
            }
            startActivity(intent)
        }
    }
}

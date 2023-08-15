package com.example.gbtest08

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var startDayButton: Button
    private lateinit var addFuelButton: Button
    private lateinit var addExpenseButton: Button
    private lateinit var editDayButton: Button
    private lateinit var currentDateTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var vehicleTextView: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startDayButton = findViewById(R.id.startDayButton)
        addFuelButton = findViewById(R.id.addFuelButton)
        addExpenseButton = findViewById(R.id.addExpenseButton)
        editDayButton = findViewById(R.id.editDayButton)
        currentDateTextView = findViewById(R.id.currentDateTextView)
        nameTextView = findViewById(R.id.nameTextView)
        vehicleTextView = findViewById(R.id.vehicleTextView)

        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)

        // Retrieve saved user inputs from shared preferences
        val firstName = sharedPreferences.getString("firstName", "")
        val lastName = sharedPreferences.getString("lastName", "")
        val make = sharedPreferences.getString("make", "")
        val model = sharedPreferences.getString("model", "")

        // Set the values in the TextViews
        currentDateTextView.text = getCurrentDate()
        nameTextView.text = "Name: $firstName $lastName"
        vehicleTextView.text = "Vehicle: $make $model"

        startDayButton.setOnClickListener {
            // Start Day button clicked
            val intent = Intent(this@MainActivity, StartDayActivity::class.java)
            startActivity(intent)
        }

        addFuelButton.setOnClickListener {
            // Add Fuel button clicked
            val intent = Intent(this@MainActivity, AddFuelPopupActivity::class.java)
            startActivity(intent)
        }

        addExpenseButton.setOnClickListener {
            // Add Expense button clicked
            val intent = Intent(this@MainActivity, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        editDayButton.setOnClickListener {
            // Edit Day button clicked
            val intent = Intent(this@MainActivity, EditDayActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getCurrentDate(): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(currentDate)
    }
}

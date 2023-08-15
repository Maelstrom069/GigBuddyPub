package com.example.gbtest08

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gbtest08.DatabaseHelper
import java.text.SimpleDateFormat
import java.util.*

class AddFuelPopupActivity : AppCompatActivity() {

    private lateinit var editTextDate: EditText
    private lateinit var buttonEditDate: Button
    private lateinit var editTextTOFOdometer: EditText
    private lateinit var editTextGasPrice: EditText
    private lateinit var editTextTotalAmountPaid: EditText
    private lateinit var checkBoxCompleteFillup: CheckBox
    private lateinit var buttonDone: Button

    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the theme to a dialog style
       // setTheme(android.R.style.Theme_DeviceDefault_Dialog)

        // Remove the title bar for the dialog
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        // Set the content view
        setContentView(R.layout.dialog_add_fuel)

        // Initialize views and set click listeners
        editTextDate = findViewById(R.id.editTextDate)
        buttonEditDate = findViewById(R.id.buttonEditDate)
        editTextTOFOdometer = findViewById(R.id.editTextTOFOdometer)
        editTextGasPrice = findViewById(R.id.editTextGasPrice)
        editTextTotalAmountPaid = findViewById(R.id.editTextTotalAmountPaid)
        checkBoxCompleteFillup = findViewById(R.id.checkBoxCompleteFillup)
        buttonDone = findViewById(R.id.buttonDone)

        // Set click listener for editing date
        buttonEditDate.setOnClickListener {
            showDatePicker()
        }

        // Set click listener for Done button
        buttonDone.setOnClickListener {
            // Retrieve the entered data from the views
            val tofOdometer = editTextTOFOdometer.text.toString()
            val gasPrice = editTextGasPrice.text.toString()
            val totalAmountPaid = editTextTotalAmountPaid.text.toString()
            val completeFillup = checkBoxCompleteFillup.isChecked

            // Validate if all fields are filled
            if (tofOdometer.isNotEmpty() && gasPrice.isNotEmpty() && totalAmountPaid.isNotEmpty()) {
                // Save the data to the database
                saveFuelData(selectedDate, tofOdometer, gasPrice, totalAmountPaid, completeFillup)

                // Finish the activity to close the popup
                finish()
            } else {
                // Show error message if any field is empty
                Toast.makeText(this@AddFuelPopupActivity, "Please complete all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Set the current date to the "Select Date" input
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        selectedDate = dateFormat.format(currentDate)
        editTextDate.setText(selectedDate)

        // Set the size of the popup window to cover 70% of the screen width
        val metrics = resources.displayMetrics
        val screenWidth = (metrics.widthPixels * 0.7).toInt()
        window.setLayout(screenWidth, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.popup_background))
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth)
                editTextDate.setText(selectedDate)
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun saveFuelData(
        date: String,
        tofOdometer: String,
        gasPrice: String,
        totalAmountPaid: String,
        completeFillup: Boolean
    ) {
        val dbHelper = DatabaseHelper(this)
        dbHelper.insertFuelData(date, tofOdometer, gasPrice, totalAmountPaid, completeFillup)
    }
}

package com.example.gbtest08

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gbtest08.DatabaseHelper
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {
    private lateinit var editTextExpenseDate: EditText
    private lateinit var buttonEditExpenseDate: Button
    private lateinit var expenseAmountEditText: EditText
    private lateinit var expenseTypeEditText: EditText
    private lateinit var buttonExpenseDone: Button

    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the theme to a dialog style
        //setTheme(android.R.style.Theme_DeviceDefault_Dialog)

        // Remove the title bar for the dialog
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)


        // Set the content view
        setContentView(R.layout.dialog_add_expense)

        // Initialize views and set click listeners
        editTextExpenseDate = findViewById(R.id.editTextExpenseDate)
        buttonEditExpenseDate = findViewById(R.id.buttonEditExpenseDate)
        expenseAmountEditText = findViewById(R.id.expenseAmountEditText)
        expenseTypeEditText = findViewById(R.id.expenseTypeEditText)
        buttonExpenseDone = findViewById(R.id.buttonExpenseDone)

        // Set click listener for editing date
        buttonEditExpenseDate.setOnClickListener {
            showDatePicker()
        }

        // Set click listener for Done button
        buttonExpenseDone.setOnClickListener {
            // Retrieve the entered data from the views
            val amount = expenseAmountEditText.text.toString()
            val type = expenseTypeEditText.text.toString()

            // Validate if all fields are filled
            if (amount.isNotEmpty() && type.isNotEmpty()) {
                // Save the data to the database
                saveExpenseData(selectedDate, amount, type)

                // Finish the activity to close the popup
                finish()
            } else {
                // Show error message if any field is empty
                // ...
            }
        }

        // Set the current date to the "Select Date" input
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        selectedDate = dateFormat.format(currentDate)
        editTextExpenseDate.setText(selectedDate)

        // Set the size of the popup window to cover 80% of the screen width
        val metrics = resources.displayMetrics
        val screenWidth = (metrics.widthPixels * 0.8).toInt()
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
                selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", month + 1, dayOfMonth, year)
                editTextExpenseDate.setText(selectedDate)
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun saveExpenseData(date: String, amount: String, type: String) {
        val dbHelper = DatabaseHelper(this)
        // Save expense data to the database
        // ...
    }
}

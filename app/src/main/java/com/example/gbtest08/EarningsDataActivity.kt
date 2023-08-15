package com.example.gbtest08

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EarningsDataActivity : AppCompatActivity() {
    private var currentDateTextView: TextView? = null
    private var amountEarnedTextView: TextView? = null
    private var expensesTextView: TextView? = null
    private var milesDrivenTextView: TextView? = null
    private var netEarningsTextView: TextView? = null

    // Sample values for demonstration purposes
    private val amountEarned = 250.50
    private val expenses = 50.75
    private val milesDriven = 120.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earnings_data)
        title = "Earnings Data"
        currentDateTextView = findViewById(R.id.currentDateTextView)
        amountEarnedTextView = findViewById(R.id.amountEarnedTextView)
        expensesTextView = findViewById(R.id.expensesTextView)
        milesDrivenTextView = findViewById(R.id.milesDrivenTextView)
        netEarningsTextView = findViewById(R.id.netEarningsTextView)

        // Set the current date in mm/dd/yyyy format
        val currentDate = "06/15/2023"
        currentDateTextView?.text = "Day: $currentDate"

        // Update the values in the table
        amountEarnedTextView?.text = String.format("%.2f", amountEarned)
        expensesTextView?.text = String.format("%.2f", expenses)
        milesDrivenTextView?.text = String.format("%.1f", milesDriven)

        // Calculate and display the net earnings
        val netEarnings = amountEarned - expenses
        netEarningsTextView?.text = String.format("%.2f", netEarnings)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_earnings_data, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_settings) {
            // Handle menu item click (e.g., open settings activity)
            true
        } else super.onOptionsItemSelected(item)
    }
}

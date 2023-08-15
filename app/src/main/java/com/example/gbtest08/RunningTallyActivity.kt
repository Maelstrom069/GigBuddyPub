package com.example.gbtest08

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class RunningTallyActivity : AppCompatActivity() {
    private lateinit var gigAppsListTextView: TextView
    private lateinit var currentInputContainer: LinearLayout
    private lateinit var dayTotalTextView: TextView
    private lateinit var updateButton: Button
    private lateinit var endDayButton: Button
    private lateinit var endOdometerInput: EditText

    private lateinit var addFuelButton: Button
    private lateinit var addExpenseButton: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var selectedGigApps: List<String>
    private lateinit var currentInputEditTexts: MutableList<EditText>
    private lateinit var updateTextViews: MutableList<TextView>

    private var dayTotal: Double = 0.0
    private var startDayOdometer: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_running_tally)
        title = "Running Tally"

        gigAppsListTextView = findViewById(R.id.gigAppsListTextView)
        currentInputContainer = findViewById(R.id.currentInputContainer)
        dayTotalTextView = findViewById(R.id.dayTotalTextView)
        updateButton = findViewById(R.id.updateButton)
        endDayButton = findViewById(R.id.endDayButton)
        endOdometerInput = findViewById(R.id.endOdometerInput)

        addFuelButton = findViewById(R.id.addFuelButton)
        addExpenseButton = findViewById(R.id.addExpenseButton)

        databaseHelper = DatabaseHelper(this)
        selectedGigApps = databaseHelper.getSelectedGigApps()

        // Set the selected Gig Apps in the gigAppsListTextView
        gigAppsListTextView.text = selectedGigApps.joinToString(", ")

        currentInputEditTexts = mutableListOf()
        updateTextViews = mutableListOf()

        createDynamicViews()

        updateButton.setOnClickListener {
            updateInputValues()
            updateRunningTally()
        }

        endDayButton.setOnClickListener {
            saveRunningTallyData()
        }

        addFuelButton.setOnClickListener {
            // Add Fuel button clicked
            val intent = Intent(this@RunningTallyActivity, AddFuelPopupActivity::class.java)
            startActivity(intent)
        }

        addExpenseButton.setOnClickListener {
            // Add Expense button clicked
            val intent = Intent(this@RunningTallyActivity, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createDynamicViews() {
        val inflater = layoutInflater

        selectedGigApps.forEach { gigApp ->
            val view = inflater.inflate(R.layout.item_gig_app, null)

            val gigAppTextView = view.findViewById<TextView>(R.id.gigAppTextView)
            val currentInputEditText = view.findViewById<EditText>(R.id.currentInputEditText)
            val updateTextView = view.findViewById<TextView>(R.id.updateTextView)

            gigAppTextView.text = gigApp // Display the selected Gig App
            currentInputEditTexts.add(currentInputEditText)
            updateTextViews.add(updateTextView)

            currentInputContainer.addView(view)
        }
    }

    private fun updateInputValues() {
        for (i in 0 until selectedGigApps.size) {
            val currentInputText = currentInputEditTexts[i].text.toString()
            val updateValue = if (currentInputText.isNotEmpty()) currentInputText else updateTextViews[i].text.toString()
            updateTextViews[i].text = updateValue
            currentInputEditTexts[i].setText("")
        }
    }

    private fun updateRunningTally() {
        var total = 0.0

        for (i in 0 until selectedGigApps.size) {
            val updateText = updateTextViews[i].text.toString()
            val update = updateText.toDoubleOrNull() ?: 0.0
            total += update
        }

        dayTotal = total
        dayTotalTextView.text = getString(R.string.total_label, DecimalFormat("$#.00").format(dayTotal))
    }

    private fun saveRunningTallyData() {
        updateInputValues()
        updateRunningTally()

        val gigAppAmounts = updateTextViews.map { it.text.toString().toDoubleOrNull() ?: 0.0 }

        val endMileage = endOdometerInput.text.toString().toIntOrNull() ?: 0
        val milesDriven = endMileage - startDayOdometer

        // Save the values to the database
        val currentDate = System.currentTimeMillis()
        databaseHelper.saveRunningTallyData(dayTotal, milesDriven, endMileage, currentDate)

        val intent = Intent(this, EndDayActivity::class.java)
        intent.putExtra("dayTotal", dayTotal)
        intent.putExtra("milesDriven", milesDriven)
        intent.putExtra("gigAppAmounts", gigAppAmounts.toDoubleArray())
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.running_tally_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuEditGigApps -> {
                val intent = Intent(this, EditGigAppsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

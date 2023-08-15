package com.example.gbtest08

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class StartDayActivity : AppCompatActivity() {
    private lateinit var startDayButton: Button
    private lateinit var startOdometerInput: EditText
    private lateinit var selectedGigAppsListView: ListView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var selectedGigApps: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_day)
        title = "Start Day"

        startDayButton = findViewById(R.id.startDayButton)
        startOdometerInput = findViewById(R.id.startDayOdometer)
        selectedGigAppsListView = findViewById(R.id.selectedGigAppsListView)

        databaseHelper = DatabaseHelper(this)

        startDayButton.setOnClickListener {
            startDay()
        }

        // Retrieve selected gig apps from the database and display in ListView
        selectedGigApps = databaseHelper.getSelectedGigApps()
        val appListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedGigApps)
        selectedGigAppsListView.adapter = appListAdapter
    }

    private fun startDay() {
        val startOdometerReading = startOdometerInput.text.toString().toIntOrNull() ?: 0

        // Save the start day data to the database
        val currentDate = System.currentTimeMillis()
        databaseHelper.saveStartDayData(startOdometerReading, currentDate, selectedGigApps)

        val intent = Intent(this, RunningTallyActivity::class.java)
        intent.putExtra("startDayOdometer", startOdometerReading)
        startActivity(intent)
    }
}

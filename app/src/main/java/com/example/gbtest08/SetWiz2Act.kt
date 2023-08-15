package com.example.gbtest08

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class SetWiz2Act : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var checkBoxDoorDash: CheckBox
    private lateinit var checkBoxUberEats: CheckBox
    private lateinit var checkBoxUberDrive: CheckBox
    private lateinit var checkBoxRoadie: CheckBox
    private lateinit var checkBoxSpark: CheckBox
    private lateinit var checkBoxInstacart: CheckBox
    private lateinit var checkBoxGrubHub: CheckBox
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_wiz2)

        dbHelper = DatabaseHelper(this)

        checkBoxDoorDash = findViewById(R.id.checkBoxDoorDash)
        checkBoxUberEats = findViewById(R.id.checkBoxUberEats)
        checkBoxUberDrive = findViewById(R.id.checkBoxUberDrive)
        checkBoxRoadie = findViewById(R.id.checkBoxRoadie)
        checkBoxSpark = findViewById(R.id.checkBoxSpark)
        checkBoxInstacart = findViewById(R.id.checkBoxInstacart)
        checkBoxGrubHub = findViewById(R.id.checkBoxGrubHub)
        nextButton = findViewById(R.id.buttonNext)

        nextButton.setOnClickListener {
            Log.d("SetWiz2Act", "Next button clicked")
            val selectedGigApps = getSelectedGigApps()
            Log.d("SetWiz2Act", "Selected Gig Apps: $selectedGigApps")

            // Save selected Gig Apps to the database
            dbHelper.saveSelectedGigApps(selectedGigApps)

            navigateToMainActivity()
        }
    }

    private fun getSelectedGigApps(): List<String> {
        val selectedGigApps = mutableListOf<String>()

        if (checkBoxDoorDash.isChecked) {
            selectedGigApps.add("DoorDash")
        }
        if (checkBoxUberEats.isChecked) {
            selectedGigApps.add("UberEats")
        }
        if (checkBoxUberDrive.isChecked) {
            selectedGigApps.add("UberDrive")
        }
        if (checkBoxRoadie.isChecked) {
            selectedGigApps.add("Roadie")
        }
        if (checkBoxSpark.isChecked) {
            selectedGigApps.add("Spark")
        }
        if (checkBoxInstacart.isChecked) {
            selectedGigApps.add("Instacart")
        }
        if (checkBoxGrubHub.isChecked) {
            selectedGigApps.add("GrubHub")
        }

        return selectedGigApps
    }

    private fun navigateToMainActivity() {
        val selectedGigAppsId = intent.getIntExtra("selectedGigAppsId", -1)
        if (selectedGigAppsId != -1) {
            Log.d("SetWiz2Act", "selectedGigAppsId: $selectedGigAppsId")
            val intent = Intent(this@SetWiz2Act, MainActivity::class.java).apply {
                putExtra("selectedGigAppsId", selectedGigAppsId)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            Log.d("SetWiz2Act", "Starting MainActivity")
            startActivity(intent)
            finish()
        } else {
            Log.e("SetWiz2Act", "selectedGigAppsId is not provided")
            // Handle the case when selectedGigAppsId is not provided
        }
    }
}

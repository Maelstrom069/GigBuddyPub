package com.example.gbtest08

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditMileageActivity : AppCompatActivity() {
    private lateinit var startOdometerInput: EditText
    private lateinit var endOdometerInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popup_edit_mileage)

        startOdometerInput = findViewById(R.id.startOdometerInput)
        endOdometerInput = findViewById(R.id.endOdometerInput)

        val confirmButton = findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            saveMileage()
        }
    }

    private fun saveMileage() {
        val startOdometer = startOdometerInput.text.toString().toIntOrNull()
        val endOdometer = endOdometerInput.text.toString().toIntOrNull()

        if (startOdometer != null && endOdometer != null) {
            val resultIntent = Intent()
            resultIntent.putExtra("startOdometer", startOdometer)
            resultIntent.putExtra("endOdometer", endOdometer)
            setResult(Activity.RESULT_OK, resultIntent)
        } else {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }
}

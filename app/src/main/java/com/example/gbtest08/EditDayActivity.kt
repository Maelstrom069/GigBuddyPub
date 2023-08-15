package com.example.gbtest08

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditDayActivity : AppCompatActivity() {
    private var earnedTotalEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit_day)
        earnedTotalEditText = findViewById(R.id.earnedTotalEditText)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Edit Day")
            .setPositiveButton("Save") { dialog, whichButton ->
                val earnedTotalString = earnedTotalEditText?.text.toString()
                if (!earnedTotalString.isEmpty()) {
                    val earnedTotal = earnedTotalString.toDouble()
                    // Perform the necessary logic for editing the day
                    // ...
                }
            }
            .setNegativeButton("Cancel") { dialog, whichButton -> dialog.dismiss() }
        val editDayDialog = dialogBuilder.create()
        editDayDialog.show()
    }
}

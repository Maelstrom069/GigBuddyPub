package com.example.gbtest08


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EditGigAppsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_app_list)
        title = "Edit Gig Apps"
    }
}

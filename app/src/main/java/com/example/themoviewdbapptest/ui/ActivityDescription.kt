package com.example.themoviewdbapptest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.themoviewdbapptest.R

class ActivityDescription : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alert_dialog_movie_descripcion)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
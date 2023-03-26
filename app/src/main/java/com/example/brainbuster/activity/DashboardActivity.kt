package com.example.brainbuster.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brainbuster.R
import com.example.brainbuster.helper.Utils

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        Utils.blackIconStatusBar(this, R.color.white)

    }
}
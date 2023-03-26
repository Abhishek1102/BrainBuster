package com.example.brainbuster.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.brainbuster.R
import com.example.brainbuster.helper.Utils

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Utils.blackIconStatusBar(this, R.color.white)

        Handler().postDelayed({
            startActivity(Intent(this,AuthenticationActivity::class.java))
            finish()
        },2000)

    }
}
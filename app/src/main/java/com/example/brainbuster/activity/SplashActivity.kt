package com.example.brainbuster.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ProgressBar
import com.example.brainbuster.R
import com.example.brainbuster.helper.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashActivity : AppCompatActivity() {

    lateinit var pb: ProgressBar
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Utils.blackIconStatusBar(this, R.color.white)

        // This method is used so that your splash activity can cover the entire screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mAuth = FirebaseAuth.getInstance()


        Handler().postDelayed({
            var user: FirebaseUser? = mAuth.currentUser
            if (user != null) {
                startActivity(Intent(this, DashboardActivity::class.java))
                this.finish()
            } else {
                startActivity(Intent(this, AuthenticationActivity::class.java))
                this.finish()

            }
        },2000)

    }
}
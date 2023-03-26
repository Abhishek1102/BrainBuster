package com.example.brainbuster.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.brainbuster.R
import com.example.brainbuster.fragment.AuthenticateFragment
import com.example.brainbuster.helper.Utils

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        addfragment(AuthenticateFragment(),"AuthenticateFragment")
        Utils.blackIconStatusBar(this, R.color.white)
    }

    private fun addfragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frame_authentication, fragment, tag)
        fragmentTransaction.commit()
    }
}
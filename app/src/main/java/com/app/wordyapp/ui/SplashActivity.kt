package com.app.wordyapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type
import com.app.wordyapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        hideSystemUI()

        val splashScreenDuration = 2000
        val mainActivityIntent = Intent(this, MainActivity::class.java)

        android.os.Handler().postDelayed({
            startActivity(mainActivityIntent)
            finish()
        }, splashScreenDuration.toLong())
    }

    private fun hideSystemUI() {
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(Type.systemBars())
    }
}
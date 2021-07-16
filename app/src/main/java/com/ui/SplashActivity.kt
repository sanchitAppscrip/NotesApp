package com.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.test.notesapp.R
import dagger.hilt.android.AndroidEntryPoint

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val SPLASH_DISPLAY_LENGTH = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
           startActivity(Intent(this,NotesListActivity::class.java))
            finish()
        }, SPLASH_DISPLAY_LENGTH)
    }

}
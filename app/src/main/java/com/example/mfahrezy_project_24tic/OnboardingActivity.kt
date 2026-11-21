package com.example.mfahrezy_project_24tic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mfahrezy_project_24tic.tutorial.TutorialActivity

// Refactored OnboardingActivity to Tutorial Screen
class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, TutorialActivity::class.java))
        finish()
    }
}

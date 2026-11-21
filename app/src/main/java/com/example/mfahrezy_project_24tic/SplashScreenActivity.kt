package com.example.mfahrezy_24_tic

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mfahrezy_project_24tic.BmiPreferences
import com.example.mfahrezy_project_24tic.LoginActivity
import com.example.mfahrezy_project_24tic.MainActivity
import com.example.mfahrezy_project_24tic.R
import com.example.mfahrezy_project_24tic.tutorial.TutorialActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// [PERTEMUAN 6] Splash Screen Activity
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // [PERTEMUAN 6] SharedPreferences: Inisialisasi helper session
        val bmiPref = BmiPreferences(this)

        lifecycleScope.launch {
            delay(2000)

            // Flow: Splash -> (jika isLogin true -> Main), (jika tutorial pernah dibuka -> Login), (jika belum -> Tutorial)
            if (bmiPref.isLogin) {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
            } else if (bmiPref.isTutorialSeen) {
                val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashScreenActivity, TutorialActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
    }
}

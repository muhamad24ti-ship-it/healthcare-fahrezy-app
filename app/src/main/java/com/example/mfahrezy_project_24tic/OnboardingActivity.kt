package com.example.mfahrezy_project_24tic

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mfahrezy_project_24tic.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    // [PERTEMUAN 4] ViewBinding: Deklarasi binding untuk OnboardingActivity
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // [PERTEMUAN 4] ViewBinding: Inisialisasi ViewBinding untuk OnboardingActivity
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // CATATAN: Ini adalah Custom View menggunakan Canvas untuk menggambar jarum BMI secara manual
        binding.bmiGaugeView.setBmiScore(22.8f, "Normal", animate = true)

        // [PERTEMUAN 4] Intent: Navigasi dari OnboardingActivity ke LoginActivity
        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // [PERTEMUAN 4] Intent: Navigasi dari OnboardingActivity ke LoginActivity
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}

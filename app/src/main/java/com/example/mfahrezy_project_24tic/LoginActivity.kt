package com.example.mfahrezy_project_24tic

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mfahrezy_project_24tic.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var bmiPref: BmiPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bmiPref = BmiPreferences(this)

        binding.btnLogin.setOnClickListener {
            val user = binding.edtUsername.text.toString().trim()
            val pass = binding.edtPassword.text.toString().trim()

            // INGAT: Disini logikanya user harus SAMA dengan password
            if (user.isNotEmpty() && user == pass) {
                bmiPref.isLogin = true
                bmiPref.username = user

                // Toast instruksi dosen: Tampilkan hasil inputan username saat login berhasil
                Toast.makeText(this, "Login Berhasil, Selamat datang $user", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                val snackbar = Snackbar.make(
                    binding.main,
                    "Login Gagal! Pastikan username dan password sama & tidak kosong.",
                    Snackbar.LENGTH_LONG
                )
                snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.logout_red))
                snackbar.setTextColor(ContextCompat.getColor(this, R.color.white))
                snackbar.show()
            }
        }
    }
}

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
    // [PERTEMUAN 4] ViewBinding: Deklarasi variabel binding untuk ActivityLogin
    private lateinit var binding: ActivityLoginBinding
    // [PERTEMUAN 6] SharedPreferences: Deklarasi helper penyimpanan session
    private lateinit var bmiPref: BmiPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // [PERTEMUAN 4] ViewBinding: Inisialisasi ViewBinding
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
                // [PERTEMUAN 6] SharedPreferences: Menyimpan data session login dan username
                bmiPref.isLogin = true
                bmiPref.username = user

                // [PERTEMUAN 3] Toast: Menampilkan pesan Toast dengan username saat login berhasil
                Toast.makeText(this, "Login Berhasil, Selamat datang $user", Toast.LENGTH_SHORT).show()

                // [PERTEMUAN 4] Intent: Pindah halaman dari LoginActivity ke MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // Snackbar saat login gagal
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

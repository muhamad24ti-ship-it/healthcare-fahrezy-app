package com.example.mfahrezy_project_24tic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mfahrezy_project_24tic.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    // [PERTEMUAN 4] ViewBinding: Deklarasi _binding untuk DetailActivity
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // [PERTEMUAN 4] ViewBinding: Inisialisasi ViewBinding untuk DetailActivity
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // [PERTEMUAN 6] Toolbar: Listener tombol panah kembali pada Toolbar Detail
        binding.toolbarDetail.setNavigationOnClickListener {
            finish()
        }

        binding.btnKembali.setOnClickListener {
            finish()
        }
    }
}

package com.example.mfahrezy_project_24tic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.mfahrezy_project_24tic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Set Toolbar Initial
        binding.toolbarMain.title = "Seimbang"

        // Default Fragment: HomeFragment
        if (savedInstanceState == null) {
            loadFragment(HomeFragment(), "Seimbang - Beranda")
        }

        // Listener Bottom Navigation 4 Menu
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val (fragment, title) = when (item.itemId) {
                R.id.nav_home -> Pair(HomeFragment(), "Seimbang - Beranda")
                R.id.nav_calculate -> Pair(CalculateFragment(), "Hitung BMI")
                R.id.nav_history -> Pair(HistoryFragment(), "Riwayat & Grafik")
                R.id.nav_profile -> Pair(ProfileFragment(), "Profil & Pengaturan")
                else -> Pair(HomeFragment(), "Seimbang")
            }
            loadFragment(fragment, title)
            true
        }
    }

    private fun loadFragment(fragment: Fragment, title: String) {
        binding.toolbarMain.title = title
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}

package com.example.mfahrezy_project_24tic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.mfahrezy_project_24tic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // [PERTEMUAN 4] ViewBinding: Deklarasi variabel binding untuk MainActivity
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // [PERTEMUAN 4] ViewBinding: Inisialisasi ViewBinding untuk MainActivity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // [PERTEMUAN 6] Toolbar: Inisialisasi dan penyetelan judul Toolbar utama
        binding.toolbarMain.title = "Seimbang"

        // Default Fragment: HomeFragment
        if (savedInstanceState == null) {
            loadFragment(HomeFragment(), "Seimbang - Beranda")
        }

        // [PERTEMUAN 7] BottomNavigationView: Listener navigasi 4 menu tab (Beranda, Hitung, Riwayat, Profil)
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
        // [PERTEMUAN 6] Toolbar: Mengubah judul Toolbar secara dinamis saat berpindah tab
        binding.toolbarMain.title = title
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}

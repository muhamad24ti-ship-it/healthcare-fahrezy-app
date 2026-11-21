package com.example.mfahrezy_project_24tic

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
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

        // [PERTEMUAN 6] Toolbar: Inisialisasi Toolbar utama & menu icon garis 3 (hamburger)
        binding.toolbarMain.title = "Seimbang"
        binding.toolbarMain.inflateMenu(R.menu.top_app_bar_menu)

        binding.toolbarMain.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.action_hamburger) {
                val anchorView = findViewById<View>(R.id.action_hamburger) ?: binding.toolbarMain
                showHamburgerPopupMenu(anchorView)
                true
            } else {
                false
            }
        }

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

    private fun showHamburgerPopupMenu(anchorView: View) {
        val popupMenu = PopupMenu(this, anchorView)
        popupMenu.menu.add("Detail Projek")
        popupMenu.menu.add("Artikel Sehat")

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.title) {
                "Detail Projek" -> {
                    // [PERTEMUAN 4] Intent: Navigasi dari Menu Toolbar ke DetailActivity
                    startActivity(Intent(this, DetailActivity::class.java))
                    true
                }
                "Artikel Sehat" -> {
                    // CATATAN: Intent ke WebViewActivity untuk artikel kesehatan
                    startActivity(Intent(this, WebViewActivity::class.java))
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun loadFragment(fragment: Fragment, title: String) {
        // [PERTEMUAN 6] Toolbar: Mengubah judul Toolbar secara dinamis saat berpindah tab
        binding.toolbarMain.title = title

        if (fragment is HomeFragment) {
            binding.toolbarMain.setBackgroundColor(Color.TRANSPARENT)
            binding.appBarLayout.elevation = 0f
        } else {
            binding.toolbarMain.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_teal))
            binding.appBarLayout.elevation = 4f
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}

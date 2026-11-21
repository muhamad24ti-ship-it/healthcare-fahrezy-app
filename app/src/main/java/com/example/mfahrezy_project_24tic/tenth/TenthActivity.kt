package com.example.mfahrezy_project_24tic.tenth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mfahrezy_project_24tic.databinding.ActivityTenthBinding
import com.google.android.material.tabs.TabLayoutMediator

class TenthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTenthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTenthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup Toolbar Back Navigation
        binding.toolbarTenth.setNavigationOnClickListener {
            finish()
        }

        // Setup ViewPager2 Adapter
        val adapter = TenthTabsAdapter(this)
        binding.viewPagerTenth.adapter = adapter

        // Setup TabLayoutMediator untuk menghubungkan TabLayout & ViewPager2
        TabLayoutMediator(binding.tabLayoutTenth, binding.viewPagerTenth) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Info"
                    tab.setIcon(android.R.drawable.ic_dialog_info)
                }
                1 -> {
                    tab.text = "Riwayat"
                    tab.setIcon(android.R.drawable.ic_menu_recent_history)
                }
            }
        }.attach()
    }
}

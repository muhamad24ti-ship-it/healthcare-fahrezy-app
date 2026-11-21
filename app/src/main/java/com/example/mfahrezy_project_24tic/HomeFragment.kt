package com.example.mfahrezy_project_24tic

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mfahrezy_project_24tic.data.AppDatabase
import com.example.mfahrezy_project_24tic.databinding.FragmentHomeBinding
import com.example.mfahrezy_project_24tic.tenth.TenthActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    // [PERTEMUAN 4] ViewBinding: Deklarasi _binding untuk FragmentHome
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bmiPref: BmiPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // [PERTEMUAN 4] ViewBinding: Inisialisasi ViewBinding untuk FragmentHome
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // [PERTEMUAN 6] SharedPreferences: Membaca nama username terdaftar untuk greeting
        bmiPref = BmiPreferences(requireContext())
        binding.tvUsername.text = bmiPref.username

        loadLatestBmiFromRoom()

        // Setup listener untuk 6 menu tombol di GridLayout
        setupMenuGrid()

        // Navigasi ke TenthActivity (Pertemuan 10: TabLayout & RecyclerView)
        binding.btnToTenth.setOnClickListener {
            val intent = Intent(requireContext(), TenthActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadLatestBmiFromRoom()
    }

    private fun loadLatestBmiFromRoom() {
        // Muat data BMI terakhir dari Room Database
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val roomRecords = AppDatabase.getInstance(requireContext()).bmiRecordDao().getAll()
            withContext(Dispatchers.Main) {
                if (roomRecords.isNotEmpty()) {
                    val latest = roomRecords[0]
                    binding.bmiGaugeHome.setBmiScore(latest.bmiScore.toFloat(), latest.bmiCategory, animate = true)
                } else {
                    val history = bmiPref.getBmiHistory()
                    if (history.isNotEmpty()) {
                        val latest = history[0]
                        binding.bmiGaugeHome.setBmiScore(latest.bmiScore, latest.status, animate = true)
                    } else {
                        binding.bmiGaugeHome.setBmiScore(22.8f, "Normal", animate = true)
                    }
                }
            }
        }
    }

    private fun setupMenuGrid() {
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // 1. Hitung BMI -> Pindah ke Tab CalculateFragment
        binding.btnMenu1.setOnClickListener {
            if (bottomNav != null) {
                bottomNav.selectedItemId = R.id.nav_calculate
            } else {
                Toast.makeText(requireContext(), "Menu Hitung BMI", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. Riwayat -> Pindah ke Tab HistoryFragment
        binding.btnMenu2.setOnClickListener {
            if (bottomNav != null) {
                bottomNav.selectedItemId = R.id.nav_history
            } else {
                Toast.makeText(requireContext(), "Menu Riwayat", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. Grafik -> Pindah ke Tab HistoryFragment
        binding.btnMenu3.setOnClickListener {
            if (bottomNav != null) {
                bottomNav.selectedItemId = R.id.nav_history
            } else {
                Toast.makeText(requireContext(), "Menu Grafik", Toast.LENGTH_SHORT).show()
            }
        }

        // 4. Tips Sehat -> Tampilkan Toast
        binding.btnMenu4.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Tips Sehat", Toast.LENGTH_SHORT).show()
        }

        // 5. Artikel -> Buka WebViewActivity
        binding.btnMenu5.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            startActivity(intent)
        }

        // 6. Profil -> Pindah ke Tab ProfileFragment
        binding.btnMenu6.setOnClickListener {
            if (bottomNav != null) {
                bottomNav.selectedItemId = R.id.nav_profile
            } else {
                Toast.makeText(requireContext(), "Menu Profil", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

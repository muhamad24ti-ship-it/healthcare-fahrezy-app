package com.example.mfahrezy_project_24tic

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mfahrezy_project_24tic.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bmiPref: BmiPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bmiPref = BmiPreferences(requireContext())
        binding.tvUsername.text = bmiPref.username

        // Muat data BMI terakhir dari history jika ada
        val history = bmiPref.getBmiHistory()
        if (history.isNotEmpty()) {
            val latest = history[0]
            binding.bmiGaugeHome.setBmiScore(latest.bmiScore, latest.status, animate = true)
        } else {
            binding.bmiGaugeHome.setBmiScore(22.8f, "Normal", animate = true)
        }

        // Navigasi ke DetailActivity (fitur existing)
        binding.btnKeDetail.setOnClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

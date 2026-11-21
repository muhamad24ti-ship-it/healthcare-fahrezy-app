package com.example.mfahrezy_project_24tic.tenth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mfahrezy_project_24tic.databinding.FragmentTabDuaBinding

class TabDuaFragment : Fragment() {

    private var _binding: FragmentTabDuaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabDuaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Membuat data dummy minimal 10 item untuk RecyclerView
        val dummyData = listOf(
            BmiRecord("20 Mei 2024, 08:00", "22.5", "Normal"),
            BmiRecord("21 Mei 2024, 09:15", "18.2", "Kurus"),
            BmiRecord("22 Mei 2024, 10:30", "24.8", "Normal"),
            BmiRecord("23 Mei 2024, 07:45", "26.3", "Gemuk"),
            BmiRecord("24 Mei 2024, 11:20", "21.9", "Normal"),
            BmiRecord("25 Mei 2024, 14:10", "30.5", "Obesitas"),
            BmiRecord("26 Mei 2024, 16:05", "23.4", "Normal"),
            BmiRecord("27 Mei 2024, 08:50", "27.1", "Gemuk"),
            BmiRecord("28 Mei 2024, 19:40", "17.9", "Kurus"),
            BmiRecord("29 Mei 2024, 07:10", "22.8", "Normal"),
            BmiRecord("30 Mei 2024, 12:00", "25.0", "Gemuk")
        )

        // Menyiapkan RecyclerView & Adapter
        val adapter = BmiRecordAdapter(dummyData) { record ->
            Toast.makeText(
                requireContext(),
                "Melihat detail BMI: ${record.skorBmi}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvBmiHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBmiHistory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

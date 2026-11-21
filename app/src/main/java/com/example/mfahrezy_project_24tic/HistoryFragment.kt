package com.example.mfahrezy_project_24tic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mfahrezy_project_24tic.data.AppDatabase
import com.example.mfahrezy_project_24tic.data.entity.BmiRecordEntity
import com.example.mfahrezy_project_24tic.databinding.FragmentHistoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryFragment : Fragment() {

    // [PERTEMUAN 4] ViewBinding: Deklarasi _binding untuk FragmentHistory
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var bmiPref: BmiPreferences
    private lateinit var adapter: BmiHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // [PERTEMUAN 4] ViewBinding: Inisialisasi ViewBinding untuk FragmentHistory
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bmiPref = BmiPreferences(requireContext())
        setupRecyclerView()
        setupChipGroupFilter()
        loadHistoryData()
    }

    override fun onResume() {
        super.onResume()
        loadHistoryData()
    }

    private fun setupRecyclerView() {
        adapter = BmiHistoryAdapter(
            onDeleteClick = { record ->
                deleteRecordFromRoom(record)
            }
        )
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter
    }

    private fun setupChipGroupFilter() {
        // Filter listener untuk ChipGroup riwayat BMI
        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            val checkedId = checkedIds.firstOrNull() ?: R.id.chipSemua
            loadHistoryData(checkedId)
        }
    }

    private fun loadHistoryData(targetCheckedId: Int? = null) {
        val checkedId = targetCheckedId ?: binding.chipGroupFilter.checkedChipId
        val activeId = if (checkedId != View.NO_ID) checkedId else R.id.chipSemua

        // Membaca daftar riwayat BMI dari Room Database menggunakan Coroutine
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val allHistory = AppDatabase.getInstance(requireContext()).bmiRecordDao().getAll()
            withContext(Dispatchers.Main) {
                filterHistoryData(allHistory, activeId)
            }
        }
    }

    private fun filterHistoryData(allHistory: List<BmiRecordEntity>, checkedId: Int) {
        // Set data grafik tren dengan seluruh riwayat dari Room
        binding.bmiChartView.setBmiDataFromEntity(allHistory)

        val filteredList = when (checkedId) {
            R.id.chipKurus -> allHistory.filter {
                it.bmiCategory.equals("Kurus", ignoreCase = true) || it.bmiScore < 18.5
            }
            R.id.chipNormal -> allHistory.filter {
                it.bmiCategory.equals("Normal", ignoreCase = true) || (it.bmiScore >= 18.5 && it.bmiScore < 25.0)
            }
            R.id.chipGemuk -> allHistory.filter {
                it.bmiCategory.equals("Gemuk", ignoreCase = true) || it.bmiCategory.equals("Obesitas", ignoreCase = true) || it.bmiScore >= 25.0
            }
            else -> allHistory
        }

        if (filteredList.isEmpty()) {
            if (allHistory.isNotEmpty()) {
                Toast.makeText(requireContext(), "Data riwayat untuk kategori ini tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
            binding.tvEmptyHistory.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.GONE
        } else {
            binding.tvEmptyHistory.visibility = View.GONE
            binding.rvHistory.visibility = View.VISIBLE

            // Update RecyclerView Adapter dengan data Room Database
            adapter.updateData(filteredList)
        }
    }

    private fun deleteRecordFromRoom(record: BmiRecordEntity) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(requireContext()).bmiRecordDao().delete(record)
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Data riwayat berhasil dihapus dari Room Database", Toast.LENGTH_SHORT).show()
                loadHistoryData()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

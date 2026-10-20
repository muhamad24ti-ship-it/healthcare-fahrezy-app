package com.example.mfahrezy_project_24tic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.mfahrezy_project_24tic.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    // [PERTEMUAN 4] ViewBinding: Deklarasi _binding untuk FragmentHistory
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var bmiPref: BmiPreferences

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
        loadHistoryData()
    }

    override fun onResume() {
        super.onResume()
        loadHistoryData()
    }

    private fun loadHistoryData() {
        // [PERTEMUAN 6] SharedPreferences: Membaca daftar riwayat BMI dari JSON
        val historyList = bmiPref.getBmiHistory()
        if (historyList.isEmpty()) {
            binding.tvEmptyHistory.visibility = View.VISIBLE
            binding.lvHistory.visibility = View.GONE
        } else {
            binding.tvEmptyHistory.visibility = View.GONE
            binding.lvHistory.visibility = View.VISIBLE
            
            // [PERTEMUAN 7] ListView & Custom Adapter: Inisialisasi ListView dengan BmiHistoryListViewAdapter
            val adapter = BmiHistoryListViewAdapter(requireContext(), historyList)
            binding.lvHistory.adapter = adapter
            setListViewHeightBasedOnChildren(binding.lvHistory)
            
            // CATATAN: Ini adalah custom drawing untuk grafik garis tren BMI.
            binding.bmiChartView.setBmiData(historyList)
        }
    }

    private fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        var totalHeight = 0
        val desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.AT_MOST)
        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(desiredWidth, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            totalHeight += listItem.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + (listView.dividerHeight * (listAdapter.count - 1))
        listView.layoutParams = params
        listView.requestLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

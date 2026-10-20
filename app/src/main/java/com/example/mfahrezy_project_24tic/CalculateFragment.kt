package com.example.mfahrezy_project_24tic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mfahrezy_project_24tic.databinding.FragmentCalculateBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalculateFragment : Fragment() {

    // [PERTEMUAN 4] ViewBinding: Deklarasi _binding untuk FragmentCalculate
    private var _binding: FragmentCalculateBinding? = null
    private val binding get() = _binding!!
    private lateinit var bmiPref: BmiPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // [PERTEMUAN 4] ViewBinding: Inisialisasi ViewBinding untuk FragmentCalculate
        _binding = FragmentCalculateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bmiPref = BmiPreferences(requireContext())

        // Setup Sliders initial text
        updateHeightLabel(binding.sliderHeight.value)
        updateWeightLabel(binding.sliderWeight.value)

        binding.sliderHeight.addOnChangeListener { _, value, _ ->
            updateHeightLabel(value)
        }

        binding.sliderWeight.addOnChangeListener { _, value, _ ->
            updateWeightLabel(value)
        }

        // Default Result Gauge
        binding.bmiGaugeResult.setBmiScore(22.8f, "Normal", animate = false)
        binding.tvAdvice.text = "Gunakan form di atas untuk menghitung BMI Anda secara akurat."

        binding.btnCalculate.setOnClickListener {
            calculateBmiAndSave()
        }
    }

    private fun updateHeightLabel(heightCm: Float) {
        binding.tvHeightValue.text = String.format(Locale.US, "%.0f cm", heightCm)
    }

    private fun updateWeightLabel(weightKg: Float) {
        binding.tvWeightValue.text = String.format(Locale.US, "%.0f kg", weightKg)
    }

    private fun calculateBmiAndSave() {
        val heightCm = binding.sliderHeight.value
        val weightKg = binding.sliderWeight.value
        val ageInput = binding.edtAge.text.toString().toIntOrNull() ?: 22

        val selectedGenderId = binding.toggleGender.checkedButtonId
        val gender = if (selectedGenderId == R.id.btnGenderFemale) "Wanita" else "Pria"

        val heightMeter = heightCm / 100f
        if (heightMeter <= 0) return

        val bmiScore = weightKg / (heightMeter * heightMeter)

        val status = when {
            bmiScore < 18.5f -> "Kurus"
            bmiScore in 18.5f..24.9f -> "Normal"
            bmiScore in 25.0f..29.9f -> "Gemuk"
            else -> "Obesitas"
        }

        val advice = when (status) {
            "Kurus" -> "BMI Anda di bawah ideal. Tingkatkan asupan kalori bernutrisi dan konsultasikan dengan konselor gizi."
            "Normal" -> "BMI Anda ideal! Pertahankan pola makan sehat, olahraga teratur, dan istirahat yang cukup."
            "Gemuk" -> "BMI Anda tergolong berlebih. Kurangi gula dan makanan olahan, serta tingkatkan aktivitas fisik harian."
            else -> "BMI Anda dalam rentang obesitas. Sangat disarankan untuk memulai program diet sehat dan olahraga rutin."
        }

        // Animate Gauge & set advice
        binding.bmiGaugeResult.setBmiScore(bmiScore, status, animate = true)
        binding.tvAdvice.text = advice

        // [PERTEMUAN 6] SharedPreferences: Menyimpan record hasil perhitungan BMI ke SharedPreferences (JSON Format)
        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        val record = BmiRecord(
            dateString = dateFormat.format(Date()),
            bmiScore = bmiScore,
            status = status,
            weightKg = weightKg,
            heightCm = heightCm,
            ageYears = ageInput,
            gender = gender
        )
        bmiPref.saveBmiRecord(record)

        // [PERTEMUAN 3] Snackbar: Menampilkan Snackbar melayang di atas BottomNavigationView saat berhasil hitung BMI
        val activityView = requireActivity().findViewById<View>(R.id.main) ?: binding.root
        val bottomNav = requireActivity().findViewById<View>(R.id.bottomNavigation)
        val snackbar = Snackbar.make(activityView, "Data berhasil dihitung & disimpan!", Snackbar.LENGTH_SHORT)
        if (bottomNav != null) {
            snackbar.anchorView = bottomNav
        }
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.primary_teal))
        snackbar.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

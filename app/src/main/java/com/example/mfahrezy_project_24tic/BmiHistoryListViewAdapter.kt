package com.example.mfahrezy_project_24tic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.mfahrezy_project_24tic.databinding.ItemBmiHistoryBinding
import java.util.Locale

class BmiHistoryListViewAdapter(
    context: Context,
    private val records: List<BmiRecord>
) : ArrayAdapter<BmiRecord>(context, 0, records) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemBmiHistoryBinding
        val view: View

        if (convertView == null) {
            binding = ItemBmiHistoryBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
            view = binding.root
            view.tag = binding
        } else {
            view = convertView
            binding = convertView.tag as ItemBmiHistoryBinding
        }

        val record = getItem(position)
        if (record != null) {
            binding.tvDate.text = record.dateString
            binding.tvStatusChip.text = record.status.uppercase(Locale.US)
            binding.tvBmiScore.text = String.format(Locale.US, "%.1f", record.bmiScore)
            binding.tvDetails.text = String.format(
                Locale.US,
                "BB: %.1f kg  |  TB: %.1f cm  |  Usia: %d th  |  %s",
                record.weightKg,
                record.heightCm,
                record.ageYears,
                record.gender
            )

            val statusColor = when (record.status.lowercase(Locale.US)) {
                "kurus", "underweight" -> ContextCompat.getColor(context, R.color.bmi_underweight)
                "normal" -> ContextCompat.getColor(context, R.color.bmi_normal)
                "gemuk", "overweight" -> ContextCompat.getColor(context, R.color.bmi_overweight)
                else -> ContextCompat.getColor(context, R.color.bmi_obese)
            }
            binding.tvStatusChip.setTextColor(statusColor)
        }

        return view
    }
}

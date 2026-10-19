package com.example.mfahrezy_project_24tic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mfahrezy_project_24tic.databinding.ItemBmiHistoryBinding
import java.util.Locale

class BmiHistoryAdapter(
    private var records: List<BmiRecord> = emptyList()
) : RecyclerView.Adapter<BmiHistoryAdapter.HistoryViewHolder>() {

    fun updateData(newRecords: List<BmiRecord>) {
        records = newRecords
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemBmiHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(records[position])
    }

    override fun getItemCount(): Int = records.size

    class HistoryViewHolder(private val binding: ItemBmiHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(record: BmiRecord) {
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

            // Dynamic Chip Color based on status
            val context = binding.root.context
            val statusColor = when (record.status.lowercase(Locale.US)) {
                "kurus", "underweight" -> ContextCompat.getColor(context, R.color.bmi_underweight)
                "normal" -> ContextCompat.getColor(context, R.color.bmi_normal)
                "gemuk", "overweight" -> ContextCompat.getColor(context, R.color.bmi_overweight)
                else -> ContextCompat.getColor(context, R.color.bmi_obese)
            }
            binding.tvStatusChip.setTextColor(statusColor)
        }
    }
}

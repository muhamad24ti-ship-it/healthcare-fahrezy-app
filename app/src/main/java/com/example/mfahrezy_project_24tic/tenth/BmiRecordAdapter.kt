package com.example.mfahrezy_project_24tic.tenth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mfahrezy_project_24tic.R
import com.example.mfahrezy_project_24tic.databinding.ItemBmiRecordBinding

class BmiRecordAdapter(
    private val listRecord: List<BmiRecord>,
    private val onItemClick: (BmiRecord) -> Unit
) : RecyclerView.Adapter<BmiRecordAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemBmiRecordBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBmiRecordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listRecord[position]
        with(holder.binding) {
            tvTanggal.text = item.tanggal
            tvSkorBmi.text = item.skorBmi
            tvKategori.text = item.kategori

            // Penyesuaian warna kategori
            val context = root.context
            val colorRes = when (item.kategori.lowercase()) {
                "kurus" -> R.color.bmi_underweight
                "normal" -> R.color.bmi_normal
                "gemuk" -> R.color.bmi_overweight
                else -> R.color.bmi_obese
            }
            tvKategori.setTextColor(ContextCompat.getColor(context, colorRes))

            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = listRecord.size
}

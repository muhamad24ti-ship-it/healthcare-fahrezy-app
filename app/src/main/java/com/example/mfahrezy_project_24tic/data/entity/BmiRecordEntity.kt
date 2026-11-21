package com.example.mfahrezy_project_24tic.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bmi_records")
data class BmiRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val weight: Double,
    val height: Double,
    val bmiScore: Double,
    val bmiCategory: String,
    val timestamp: Long = System.currentTimeMillis(),
    val dateString: String = "",
    val ageYears: Int = 0,
    val gender: String = "Pria"
)

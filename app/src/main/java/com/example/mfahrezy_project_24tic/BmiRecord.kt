package com.example.mfahrezy_project_24tic

import java.util.UUID

data class BmiRecord(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val dateString: String = "",
    val bmiScore: Float = 0f,
    val status: String = "Normal",
    val weightKg: Float = 0f,
    val heightCm: Float = 0f,
    val ageYears: Int = 0,
    val gender: String = "Pria"
)

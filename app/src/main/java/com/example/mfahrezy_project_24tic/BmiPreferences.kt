package com.example.mfahrezy_project_24tic

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

class BmiPreferences(context: Context) {
    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "user_pref"
        private const val KEY_IS_LOGIN = "isLogin"
        private const val KEY_USERNAME = "username"
        private const val KEY_GENDER = "gender"
        private const val KEY_BMI_HISTORY = "bmi_history"
    }

    var isLogin: Boolean
        get() = pref.getBoolean(KEY_IS_LOGIN, false)
        set(value) = pref.edit().putBoolean(KEY_IS_LOGIN, value).apply()

    var username: String
        get() = pref.getString(KEY_USERNAME, "User") ?: "User"
        set(value) = pref.edit().putString(KEY_USERNAME, value).apply()

    var gender: String
        get() = pref.getString(KEY_GENDER, "Pria") ?: "Pria"
        set(value) = pref.edit().putString(KEY_GENDER, value).apply()

    fun saveBmiRecord(record: BmiRecord) {
        val history = getBmiHistory().toMutableList()
        history.add(0, record)
        val jsonArray = JSONArray()
        for (item in history) {
            val jsonObject = JSONObject().apply {
                put("id", item.id)
                put("timestamp", item.timestamp)
                put("dateString", item.dateString)
                put("bmiScore", item.bmiScore.toDouble())
                put("status", item.status)
                put("weightKg", item.weightKg.toDouble())
                put("heightCm", item.heightCm.toDouble())
                put("ageYears", item.ageYears)
                put("gender", item.gender)
            }
            jsonArray.put(jsonObject)
        }
        pref.edit().putString(KEY_BMI_HISTORY, jsonArray.toString()).apply()
    }

    fun getBmiHistory(): List<BmiRecord> {
        val jsonString = pref.getString(KEY_BMI_HISTORY, null) ?: return emptyList()
        val list = mutableListOf<BmiRecord>()
        try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    BmiRecord(
                        id = obj.optString("id"),
                        timestamp = obj.optLong("timestamp"),
                        dateString = obj.optString("dateString"),
                        bmiScore = obj.optDouble("bmiScore", 0.0).toFloat(),
                        status = obj.optString("status", "Normal"),
                        weightKg = obj.optDouble("weightKg", 0.0).toFloat(),
                        heightCm = obj.optDouble("heightCm", 0.0).toFloat(),
                        ageYears = obj.optInt("ageYears", 0),
                        gender = obj.optString("gender", "Pria")
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun clearSession() {
        pref.edit().clear().apply()
    }
}

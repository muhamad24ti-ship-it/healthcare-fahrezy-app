package com.example.mfahrezy_project_24tic.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mfahrezy_project_24tic.data.dao.BmiRecordDao
import com.example.mfahrezy_project_24tic.data.entity.BmiRecordEntity

@Database(entities = [BmiRecordEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bmiRecordDao(): BmiRecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bmi_database"
                )
                // .fallbackToDestructiveMigration() // SENSITIF: Hanya untuk darurat, jangan dipakai di production agar data tidak terhapus
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

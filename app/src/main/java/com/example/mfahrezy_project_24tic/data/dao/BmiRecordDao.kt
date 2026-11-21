package com.example.mfahrezy_project_24tic.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mfahrezy_project_24tic.data.entity.BmiRecordEntity

@Dao
interface BmiRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: BmiRecordEntity)

    @Query("SELECT * FROM bmi_records ORDER BY timestamp DESC")
    suspend fun getAll(): List<BmiRecordEntity>

    @Delete
    suspend fun delete(record: BmiRecordEntity)
}

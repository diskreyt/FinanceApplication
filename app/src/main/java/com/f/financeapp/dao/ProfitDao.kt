package com.f.financeapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.f.financeapp.entities.Profit

@Dao
interface ProfitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIncome(data: List<Profit>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(profit : Profit)

    @Delete
    suspend fun delete(profit : Profit)

    @Query("Select * from profitTable order by date DESC")
    fun getAll(): LiveData<List<Profit>>

    @Update
    suspend fun update(profit : Profit)
}
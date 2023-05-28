package com.f.financeapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.f.financeapp.entities.Cost

@Dao
interface CostDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertExpenses(data: List<Cost>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cost : Cost)

    @Delete
    suspend fun delete(cost : Cost)

    @Query("Select * from costTable order by date DESC")
    fun getAll(): LiveData<List<Cost>>

    @Update
    suspend fun update(cost : Cost)
}
package com.f.financeapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.f.financeapp.entities.CostProfitCategory

@Dao
interface CostProfitCategoriesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategories(data: List<CostProfitCategory>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(costProfitCategory : CostProfitCategory)

    @Delete
    suspend fun delete(costProfitCategory : CostProfitCategory)

    @Query("Select * from categoriesTable order by id ASC")
    fun getAll(): LiveData<List<CostProfitCategory>>

    @Update
    suspend fun update(costProfitCategory : CostProfitCategory)

    @Query("SELECT * FROM categoriesTable WHERE id = :id")
    fun getCategoryById(id: Int): CostProfitCategory?
}
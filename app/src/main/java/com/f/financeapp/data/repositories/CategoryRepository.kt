package com.f.financeapp.data.repositories

import androidx.lifecycle.LiveData
import com.f.financeapp.dao.CostProfitCategoriesDao
import com.f.financeapp.entities.CostProfitCategory


class CategoryRepository(private val costProfitCategoriesDao: CostProfitCategoriesDao) {

    val allCategories: LiveData<List<CostProfitCategory>> = costProfitCategoriesDao.getAll()

    suspend fun insert(costProfitCategory: CostProfitCategory){
        costProfitCategoriesDao.insert(costProfitCategory)
    }

    suspend fun update(costProfitCategory: CostProfitCategory){
        costProfitCategoriesDao.update(costProfitCategory)
    }

    suspend fun delete(costProfitCategory: CostProfitCategory){
        costProfitCategoriesDao.delete(costProfitCategory)
    }

    fun getCategoryById(categoryId: Int): CostProfitCategory? {
        return costProfitCategoriesDao.getCategoryById(categoryId)
    }
}
package com.f.financeapp.data.repositories

import androidx.lifecycle.LiveData
import com.f.financeapp.dao.CostDao
import com.f.financeapp.entities.Cost


class CostRepository(private val costDao: CostDao) {
    val allExpenses: LiveData<List<Cost>> = costDao.getAll()

    suspend fun insert(cost: Cost){
        costDao.insert(cost)
    }

    suspend fun update(cost: Cost){
        costDao.update(cost)
    }

    suspend fun delete(cost: Cost){
        costDao.delete(cost)
    }
}
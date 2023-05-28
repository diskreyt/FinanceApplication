package com.f.financeapp.data.repositories

import androidx.lifecycle.LiveData
import com.f.financeapp.dao.ProfitDao
import com.f.financeapp.entities.Profit


class ProfitRepository(private val profitDao: ProfitDao) {
    val allProfit: LiveData<List<Profit>> = profitDao.getAll()

    suspend fun insert(profit: Profit){
        profitDao.insert(profit)
    }

    suspend fun update(profit: Profit){
        profitDao.update(profit)
    }

    suspend fun delete(profit: Profit){
        profitDao.delete(profit)
    }
}
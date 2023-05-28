package com.f.financeapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.f.financeapp.data.FinanceDb
import com.f.financeapp.data.repositories.ProfitRepository
import com.f.financeapp.entities.Profit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfitViewModel(application: Application) : AndroidViewModel(application) {

    val allProfit: LiveData<List<Profit>>
    val repository: ProfitRepository

    init {
        val dao = FinanceDb.getDatabase(application).getIncomeDao()
        repository = ProfitRepository(dao)
        allProfit = repository.allProfit
    }

    fun addProfit(profit: Profit) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(profit)
    }

    fun updateProfit(profit: Profit) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(profit)
    }

    fun deleteProfit(profit: Profit) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(profit)
    }
}
package com.f.financeapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.f.financeapp.data.FinanceDb
import com.f.financeapp.data.repositories.CostRepository
import com.f.financeapp.entities.Cost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CostsViewModel(application: Application) : AndroidViewModel(application) {

    val allExpenses: LiveData<List<Cost>>
    val repository: CostRepository

    init {
        val dao = FinanceDb.getDatabase(application).getExpensesDao()
        repository = CostRepository(dao)
        allExpenses = repository.allExpenses
    }

    fun addCost(cost: Cost) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(cost)
    }

    fun updateCost(cost: Cost) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(cost)
    }

    fun deleteCost(cost: Cost) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(cost)
    }
}
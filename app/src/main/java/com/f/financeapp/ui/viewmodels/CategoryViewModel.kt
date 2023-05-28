package com.f.financeapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.f.financeapp.data.FinanceDb
import com.f.financeapp.data.repositories.CategoryRepository
import com.f.financeapp.entities.CostProfitCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    val allCategories: LiveData<List<CostProfitCategory>>
    val repository: CategoryRepository

    init {
        val dao = FinanceDb.getDatabase(application).getCategoriesDao()
        repository = CategoryRepository(dao)
        allCategories = repository.allCategories
    }

    fun addCategory(costProfitCategory: CostProfitCategory) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(costProfitCategory)
    }

    fun updateCategory(costProfitCategory: CostProfitCategory) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(costProfitCategory)
    }

    fun deleteCategory(costProfitCategory: CostProfitCategory) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(costProfitCategory)
    }

    fun getCategoryById(categoryId: Int): MutableLiveData<CostProfitCategory?> {
        val result = MutableLiveData<CostProfitCategory?>()
        viewModelScope.launch(Dispatchers.IO) {
            val category = repository.getCategoryById(categoryId)
            result.postValue(category)
        }
        return result
    }

}
package com.f.financeapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.f.financeapp.R
import com.f.financeapp.ui.fragments.CategoriesFragment
import com.f.financeapp.ui.fragments.CostFragment
import com.f.financeapp.ui.fragments.ProfitFragment
import com.f.financeapp.ui.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView



class FinanceActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)
        title = "Учет расходов и доходов"
        loadFragment(CostFragment())
        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bm_expenses -> {
                    loadFragment(CostFragment())
                    title = "Расходы"
                    true
                }
                R.id.bm_income -> {
                    loadFragment(ProfitFragment())
                    title = "Доходы"
                    true
                }
                R.id.bm_categories -> {
                    title = "Категории"
                    loadFragment(CategoriesFragment())
                    true
                }
                R.id.bm_settings -> {
                    loadFragment(SettingsFragment())
                    title = "Настройки"
                    true
                }
                else -> throw AssertionError()
            }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

}
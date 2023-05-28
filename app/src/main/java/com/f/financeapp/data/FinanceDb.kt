package com.f.financeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.f.financeapp.R
import com.f.financeapp.dao.CostProfitCategoriesDao
import com.f.financeapp.dao.CostDao
import com.f.financeapp.dao.ProfitDao
import com.f.financeapp.entities.CostProfitCategory
import com.f.financeapp.entities.Cost
import com.f.financeapp.entities.Profit
import com.f.financeapp.ioThread

@Database(entities = [Cost::class, Profit::class, CostProfitCategory::class], version = 1, exportSchema = false)
abstract class FinanceDb : RoomDatabase() {

    abstract fun getExpensesDao(): CostDao
    abstract fun getCategoriesDao(): CostProfitCategoriesDao
    abstract fun getIncomeDao(): ProfitDao

    companion object {

        @Volatile private var INSTANCE: FinanceDb? = null

        fun getDatabase(context: Context): FinanceDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                FinanceDb::class.java, "finance_db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            getDatabase(context).getCategoriesDao().insertCategories(PREPOPULATE_CATEGORIES)
                        }
                    }
                })
                .build()

        val PREPOPULATE_CATEGORIES = listOf(
            CostProfitCategory("Еда", R.drawable.ic_category_products),
            CostProfitCategory("Коммунальные платежи", R.drawable.ic_category_house),
            CostProfitCategory("Домашние животные", R.drawable.ic_category_pet),
            CostProfitCategory("Кафе и рестораны", R.drawable.ic_category_restaurant),
            CostProfitCategory("Покупки для дома", R.drawable.ic_category_small_house),
            CostProfitCategory("Топливо", R.drawable.ic_category_refueling),
            CostProfitCategory("Разное", R.drawable.ic_category_other),
            CostProfitCategory("Фитнес", R.drawable.ic_category_fitness),
            CostProfitCategory("Лекарства", R.drawable.ic_category_med),
            CostProfitCategory("Телефон и свзяь", R.drawable.ic_category_phone),
            CostProfitCategory("Общественный транспорт", R.drawable.ic_category_traffic),
            CostProfitCategory("Работа", R.drawable.ic_category_bonus),
            CostProfitCategory("Вклады", R.drawable.ic_category_deposit),
            CostProfitCategory("Инвестиции", R.drawable.ic_category_investment),
            )
    }
}
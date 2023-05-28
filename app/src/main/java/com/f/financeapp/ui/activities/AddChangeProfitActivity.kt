package com.f.financeapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.f.financeapp.R
import com.f.financeapp.adapters.CategoryClickInterface
import com.f.financeapp.adapters.CategoryLongClickInterface
import com.f.financeapp.entities.CostProfitCategory
import com.f.financeapp.ui.viewmodels.CategoryViewModel
import android.widget.CalendarView.OnDateChangeListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.f.financeapp.adapters.CategoriesRVAdapter
import com.f.financeapp.entities.Profit
import com.f.financeapp.ui.viewmodels.ProfitViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer

class AddChangeProfitActivity : AppCompatActivity(), CategoryClickInterface,
    CategoryLongClickInterface {

    lateinit var etSum: EditText
    lateinit var etTitle: EditText
    lateinit var etComment: EditText
    lateinit var cvDate: CalendarView
    lateinit var btnSave: Button

    lateinit var vmIncome: ProfitViewModel
    var expenseId = -1

    lateinit var chosenDate: String

    lateinit var vmCategories: CategoryViewModel
    lateinit var rvCategories: RecyclerView

    var chosenCategoryId = -1

    lateinit var ivCategoryIcon: ImageView
    lateinit var tvCategoryName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_change_profit)

        vmCategories = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(CategoryViewModel::class.java)

        vmIncome = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ProfitViewModel::class.java)

        etSum = findViewById(R.id.etSum)
        etTitle = findViewById(R.id.etExpenseTitle)
        etComment = findViewById(R.id.etComment)
        cvDate = findViewById(R.id.cvDate)
        btnSave = findViewById(R.id.btnSave)
        ivCategoryIcon = findViewById(R.id.ivCategoryIcon)
        tvCategoryName = findViewById(R.id.tvCategoryName)

        val sdf = SimpleDateFormat("yyyy-MM-dd")

        val action = intent.getStringExtra("action")
        if (action.equals("edit")) {
            val sum = intent.getDoubleExtra("sum", 0.0)
            chosenCategoryId = intent.getIntExtra("category", -1)
            val date = intent.getStringExtra("date")
            val expenseTitle = intent.getStringExtra("title")
            val comment = intent.getStringExtra("comment")
            etSum.setText(sum.toString())
            etTitle.setText(expenseTitle)
            etComment.setText(comment)
            btnSave.text = "Обновить"
            expenseId = intent.getIntExtra("id", -1)
            title = "Изменение дохода"
            val cal = Calendar.getInstance()
            cal.setTime(sdf.parse(date))
            cvDate.date = cal.timeInMillis

            GlobalScope.launch {
                val category = vmCategories.repository.getCategoryById(chosenCategoryId)
                if (category != null) {
                    ivCategoryIcon.setImageResource(category.iconResource)
                }
                if (category != null) {
                    tvCategoryName.text = category.name
                }
            }
        } else {
            btnSave.text = "Сохранить"
            title = "Добавление дохода"
        }

        chosenDate = sdf.format(Date())

        cvDate.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            val realMonth = month + 1
            if (realMonth < 10){
                chosenDate = if (dayOfMonth < 10) {
                    "$year-0$realMonth-0$dayOfMonth"
                } else {
                    "$year-0$realMonth-$dayOfMonth"
                }
            } else {
                chosenDate = if (dayOfMonth < 10) {
                    "$year-0$realMonth-0$dayOfMonth"
                } else {
                    "$year-0$realMonth-$dayOfMonth"
                }
            }
        })

        btnSave.setOnClickListener {
            try {
                if (chosenCategoryId == -1){
                    Toast.makeText(this, "Не выбрана категория!", Toast.LENGTH_SHORT).show()
                    throw java.lang.IllegalArgumentException("Не выбрана категория!")
                }
                if (etSum.text.toString() == ""){
                    Toast.makeText(this, "Не указана сумма!", Toast.LENGTH_SHORT).show()
                    throw java.lang.IllegalArgumentException("Не указана сумма!")
                }

                val sum = etSum.text.toString().toDouble()
                val date = chosenDate
                val title = etTitle.text.toString()
                val comment = etComment.text.toString()

                if (action.equals("edit")) {
                    val updatedProfit = Profit(sum, chosenCategoryId, date, title, comment)
                    updatedProfit.id = expenseId
                    vmIncome.updateProfit(updatedProfit)
                    Toast.makeText(this, "Данные о доходах обновлены", Toast.LENGTH_LONG).show()
                } else {
                    vmIncome.addProfit(Profit(sum, chosenCategoryId, date, title, comment))
                    Toast.makeText(this, "Данные о $title добавлены", Toast.LENGTH_LONG).show()
                }
                startActivity(Intent(applicationContext, FinanceActivity::class.java))
            } catch (e: java.lang.IllegalArgumentException){

            }
        }

        rvCategories = findViewById(R.id.rvCategories)

        rvCategories.layoutManager = LinearLayoutManager(this)
        val categoriesRVAdapter = CategoriesRVAdapter(this, this, this)

        rvCategories.adapter = categoriesRVAdapter

        vmCategories.allCategories.observe(this, Observer { list ->
            list?.let {
                categoriesRVAdapter.updateList(it)
            }
        })
    }

    override fun onCategoryClick(costProfitCategory: CostProfitCategory) {
        chosenCategoryId = costProfitCategory.id
        ivCategoryIcon.setImageResource(costProfitCategory.iconResource)
        tvCategoryName.text = costProfitCategory.name
    }

    override fun onCategoryLongClick(costProfitCategory: CostProfitCategory) {
    }
}
package com.f.financeapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.f.financeapp.ui.activities.AddChangeCostActivity
import com.f.financeapp.R
import com.f.financeapp.adapters.CostClickInterface
import com.f.financeapp.adapters.CostLongClickInterface
import com.f.financeapp.adapters.ExpenseRVAdapter
import com.f.financeapp.entities.Cost
import com.f.financeapp.ui.viewmodels.CategoryViewModel
import com.f.financeapp.ui.viewmodels.CostsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CostFragment : Fragment(), CostClickInterface, CostLongClickInterface {

    lateinit var vmCost: CostsViewModel
    lateinit var vmCategories: CategoryViewModel

    lateinit var rvCost: RecyclerView
    lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvExpensesByDay = view.findViewById<TextView>(R.id.tvExpensesByDay)
        val tvExpensesByMonth = view.findViewById<TextView>(R.id.tvExpensesByMonth)
        val tvExpensesByYear = view.findViewById<TextView>(R.id.tvExpensesByYear)
        fabAdd = view.findViewById(R.id.fabAddExpense)
        rvCost = view.findViewById(R.id.rvExpenses)

        vmCategories = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(CategoryViewModel::class.java)

        vmCost = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(CostsViewModel::class.java)

        vmCost.allExpenses.observe(requireActivity(), Observer { list ->
            list?.let {
                // рассчет расходов за разные периоды времени
                val expensesByDay = list.filter { LocalDate.parse(
                    it.date,
                    DateTimeFormatter.ISO_DATE
                ) == LocalDate.now()
                }.sumOf { it.amount }
                val expensesByMonthList = list.filter { LocalDate.parse(
                    it.date,
                    DateTimeFormatter.ISO_DATE
                ) >= LocalDate.now().withDayOfMonth(1) }
                val expensesByYear = list.filter { LocalDate.parse(
                    it.date,
                    DateTimeFormatter.ISO_DATE
                ) >= LocalDate.now().withDayOfMonth(1).plusYears(-1) }.sumOf { it.amount }

                val decimalFormat = DecimalFormat("0.#")

                tvExpensesByDay.text = "Расходы за день: " + decimalFormat.format(expensesByDay) + " руб."
                tvExpensesByMonth.text = "Расходы за месяц: " + decimalFormat.format(expensesByMonthList.sumOf { it.amount }) + " руб."
                tvExpensesByYear.text = "Расходы за год: " + decimalFormat.format(expensesByYear) + " руб."

            }
        })

        rvCost.layoutManager = LinearLayoutManager(context)

        val expensesRVAdapter = ExpenseRVAdapter(requireActivity().application, this, this)

        rvCost.adapter = expensesRVAdapter

        val vmCategories: CategoryViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(CategoryViewModel::class.java)

        vmCategories.allCategories.observe(requireActivity(), Observer { list ->
            list?.let {
                expensesRVAdapter.setCategories(it)
            }
        })

        fabAdd.setOnClickListener{
            val intent = Intent(context, AddChangeCostActivity::class.java)
            startActivity(intent)
        }

        vmCost.allExpenses.observe(requireActivity(), Observer { list ->
            list?.let {
                expensesRVAdapter.updateList(it)
            }
        })
    }

    override fun onCostClick(cost: Cost) {
        val intent = Intent(context, AddChangeCostActivity::class.java)
        intent.putExtra("id", cost.id)
        intent.putExtra("date", cost.date)
        intent.putExtra("title", cost.name)
        intent.putExtra("comment", cost.comment)
        intent.putExtra("sum", cost.amount)
        intent.putExtra("category", cost.category)
        intent.putExtra("action", "edit")
        startActivity(intent)
    }

    override fun onCostLongClick(cost: Cost) {
        vmCost.deleteCost(cost)
        Toast.makeText(context, "Данные о расходах удалены", Toast.LENGTH_LONG).show()
    }
}
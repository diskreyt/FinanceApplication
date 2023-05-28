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
import com.f.financeapp.R
import com.f.financeapp.adapters.ProfitClickInterface
import com.f.financeapp.adapters.ProfitLongClickInterface
import com.f.financeapp.adapters.IncomeRVAdapter
import com.f.financeapp.entities.Profit
import com.f.financeapp.ui.activities.AddChangeProfitActivity
import com.f.financeapp.ui.viewmodels.CategoryViewModel
import com.f.financeapp.ui.viewmodels.ProfitViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProfitFragment : Fragment(), ProfitClickInterface, ProfitLongClickInterface {

    lateinit var vmProfit: ProfitViewModel
    lateinit var vmCategories: CategoryViewModel

    lateinit var rvProfit: RecyclerView
    lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvIncomeByDay = view.findViewById<TextView>(R.id.tvIncomeByDay)
        val tvIncomeByMonth = view.findViewById<TextView>(R.id.tvIncomeByMonth)
        val tvIncomeByYear = view.findViewById<TextView>(R.id.tvIncomeByYear)
        rvProfit = view.findViewById(R.id.rvIncome)
        fabAdd = view.findViewById(R.id.fabAddIncome)

        vmCategories = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(CategoryViewModel::class.java)


        vmProfit = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(ProfitViewModel::class.java)


        vmProfit.allProfit.observe(requireActivity(), Observer { list ->
            list?.let {
                // рассчет доходов за разные периоды времени
                val incomeByDay = list.filter { LocalDate.parse(
                    it.date,
                    DateTimeFormatter.ISO_DATE
                ) == LocalDate.now()
                }.sumOf { it.amount }
                val incomeByMonthList = list.filter { LocalDate.parse(
                    it.date,
                    DateTimeFormatter.ISO_DATE
                ) >= LocalDate.now().withDayOfMonth(1) }
                val incomeByYear = list.filter { LocalDate.parse(
                    it.date,
                    DateTimeFormatter.ISO_DATE
                ) >= LocalDate.now().withDayOfMonth(1).plusYears(-1)}.sumOf { it.amount }

                val decimalFormat = DecimalFormat("0.#")

                tvIncomeByDay.text = "Доходы за день: " + decimalFormat.format(incomeByDay) + " руб."
                tvIncomeByMonth.text = "Доходы за месяц: " + decimalFormat.format(incomeByMonthList.sumOf { it.amount }) + " руб."
                tvIncomeByYear.text = "Доходы за год: " +  decimalFormat.format(incomeByYear) + " руб."
            }
        })

        rvProfit.layoutManager = LinearLayoutManager(context)
        val incomeRVAdapter = IncomeRVAdapter(requireActivity(), this, this)

        rvProfit.adapter = incomeRVAdapter

        val vmCategories: CategoryViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(CategoryViewModel::class.java)

        vmCategories.allCategories.observe(requireActivity(), Observer { list ->
            list?.let {
                incomeRVAdapter.setCategories(it)
            }
        })

        fabAdd.setOnClickListener{
            val intent = Intent(context, AddChangeProfitActivity::class.java)
            startActivity(intent)
        }

        vmProfit.allProfit.observe(requireActivity(), Observer { list ->
            list?.let {
                incomeRVAdapter.updateList(it)
            }
        })
    }

    override fun onProfitClick(profit: Profit) {
        val intent = Intent(context, AddChangeProfitActivity::class.java)
        intent.putExtra("id", profit.id)
        intent.putExtra("sum", profit.amount)
        intent.putExtra("category", profit.category)
        intent.putExtra("date", profit.date)
        intent.putExtra("title", profit.name)
        intent.putExtra("comment", profit.comment)
        intent.putExtra("action", "edit")
        startActivity(intent)
    }

    override fun onProfitLongClick(profit: Profit) {
        vmProfit.deleteProfit(profit)
        Toast.makeText(context, "Данные о доходах удалены", Toast.LENGTH_LONG).show()
    }
}
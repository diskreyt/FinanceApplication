package com.f.financeapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.f.financeapp.R
import com.f.financeapp.entities.CostProfitCategory
import com.f.financeapp.entities.Profit
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class IncomeRVAdapter(
    val context: Context,
    val profitClickInterface: ProfitClickInterface,
    val profitLongClickInterface: ProfitLongClickInterface
) : RecyclerView.Adapter<IncomeRVAdapter.ViewHolder>() {

    private val allProfit = ArrayList<Profit>()
    private val categories = ArrayList<CostProfitCategory>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCategoryIcon = itemView.findViewById<ImageView>(R.id.ivCategoryIcon)
        val tvProfitName = itemView.findViewById<TextView>(R.id.tvProfitName)
        val tvComment = itemView.findViewById<TextView>(R.id.tvComment)
        val tvCategory = itemView.findViewById<TextView>(R.id.tvCategory)
        val tvAmount = itemView.findViewById<TextView>(R.id.tvAmount)
        val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.profit_rv_item, parent, false
        )
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allProfit.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val income = allProfit[position]

        holder.tvComment.text = income.comment
        val costProfitCategory: CostProfitCategory = categories.first { it.id == income.category }
        holder.tvCategory.text = costProfitCategory.name
        holder.ivCategoryIcon.setImageResource(costProfitCategory.iconResource)
        holder.tvProfitName.text = income.name
        val decimalFormat = DecimalFormat("0.#")
        holder.tvAmount.text = decimalFormat.format(income.amount) + " руб."
        val date = LocalDate.parse(income.date, DateTimeFormatter.ISO_DATE)
        holder.tvDate.text = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))


        holder.itemView.setOnClickListener{
            profitClickInterface.onProfitClick(income)
        }

        holder.itemView.setOnLongClickListener {
            profitLongClickInterface.onProfitLongClick(income)
            true
        }
    }

    fun setCategories(newList: List<CostProfitCategory>){
        categories.clear()
        categories.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateList(newList: List<Profit>){
        allProfit.clear()
        allProfit.addAll(newList)
        notifyDataSetChanged()
    }
}

interface ProfitClickInterface {
    fun onProfitClick(profit: Profit)
}

interface ProfitLongClickInterface {
    fun onProfitLongClick(profit: Profit)
}

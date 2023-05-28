package com.f.financeapp.adapters

import com.f.financeapp.entities.Cost
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.f.financeapp.R
import com.f.financeapp.entities.CostProfitCategory
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ExpenseRVAdapter(
    val context: Context,
    val costClickInterface: CostClickInterface,
    val costLongClickInterface: CostLongClickInterface,
) : RecyclerView.Adapter<ExpenseRVAdapter.ViewHolder>() {

    private val allExpens = ArrayList<Cost>()
    private val categories = ArrayList<CostProfitCategory>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategory = itemView.findViewById<TextView>(R.id.tvCategory)
        val ivCategoryIcon = itemView.findViewById<ImageView>(R.id.ivCategoryIcon)
        val tvCostName = itemView.findViewById<TextView>(R.id.tvCostName)
        val tvAmount = itemView.findViewById<TextView>(R.id.tvAmount)
        val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        val tvComment = itemView.findViewById<TextView>(R.id.tvComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cost_rv_item, parent, false
        )
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allExpens.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = allExpens[position]

        holder.tvCostName.text = expense.name
        holder.tvComment.text = expense.comment
        val costProfitCategory: CostProfitCategory = categories.first { it.id == expense.category }
        holder.tvCategory.text = "${costProfitCategory.name}"
        holder.ivCategoryIcon.setImageResource(costProfitCategory.iconResource)
        val decimalFormat = DecimalFormat("0.#")
        holder.tvAmount.text = decimalFormat.format(expense.amount) + " руб."
        val date = LocalDate.parse(expense.date, DateTimeFormatter.ISO_DATE)
        holder.tvDate.text = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))


        holder.itemView.setOnClickListener{
            costClickInterface.onCostClick(expense)
        }

        holder.itemView.setOnLongClickListener {
            costLongClickInterface.onCostLongClick(expense)
            true
        }
    }

    fun setCategories(newList: List<CostProfitCategory>){
        categories.clear()
        categories.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateList(newList: List<Cost>){
        allExpens.clear()
        allExpens.addAll(newList)
        notifyDataSetChanged()
    }
}

interface CostClickInterface {
    fun onCostClick(cost: Cost)
}

interface CostLongClickInterface {
    fun onCostLongClick(cost: Cost)
}

package com.f.financeapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "profitTable",
    foreignKeys = [
        ForeignKey(
            entity = CostProfitCategory::class,
            parentColumns = ["id"],
            childColumns = ["category"]
        )]
)
class Profit(
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "category") val category: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "comment") val comment: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
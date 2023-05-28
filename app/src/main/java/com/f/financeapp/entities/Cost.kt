package com.f.financeapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "costTable",
    foreignKeys = [
        ForeignKey(
            entity = CostProfitCategory::class,
            parentColumns = ["id"],
            childColumns = ["category"]
        )]
)
class Cost(
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "category") val category: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "comment") val comment: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
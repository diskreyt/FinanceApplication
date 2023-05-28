package com.f.financeapp.entities

import androidx.annotation.DrawableRes

data class CategoryIcon(
    @DrawableRes val iconRes: Int,
    val iconEntryName: String
)
package com.f.financeapp.extensions

import android.content.res.Resources
import androidx.annotation.ArrayRes
import com.f.financeapp.entities.CategoryIcon

fun @receiver:ArrayRes Int.toCategoryIconList(resources: Resources?): List<CategoryIcon> =
    mutableListOf<CategoryIcon>().apply {
        resources?.let {
            val categoryIcons = it.obtainTypedArray(this@toCategoryIconList)
            for (i in 0 until categoryIcons.length()) {
                val resId = categoryIcons.getResourceId(i, -1)
                val resName = it.getResourceEntryName(resId)
                this.add(CategoryIcon(resId, resName))
            }
            categoryIcons.recycle()
        }
    }
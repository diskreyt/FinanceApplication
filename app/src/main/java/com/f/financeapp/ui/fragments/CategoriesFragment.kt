package com.f.financeapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.f.financeapp.ui.activities.AddChangeCategoryActivity
import com.f.financeapp.R
import com.f.financeapp.adapters.CategoriesRVAdapter
import com.f.financeapp.adapters.CategoryClickInterface
import com.f.financeapp.adapters.CategoryLongClickInterface
import com.f.financeapp.entities.CostProfitCategory
import com.f.financeapp.ui.viewmodels.CategoryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CategoriesFragment : Fragment(), CategoryClickInterface, CategoryLongClickInterface {

    lateinit var vmCategories: CategoryViewModel
    lateinit var rvCategories: RecyclerView
    lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCategories = view.findViewById(R.id.rvCategories)
        fabAdd = view.findViewById(R.id.fabAddCategory)

        rvCategories.layoutManager = LinearLayoutManager(context)
        val categoriesRVAdapter = context?.let { CategoriesRVAdapter(it, this, this) }

        rvCategories.adapter = categoriesRVAdapter

        vmCategories = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(CategoryViewModel::class.java)

        vmCategories.allCategories.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                if (categoriesRVAdapter != null) {
                    categoriesRVAdapter.updateList(it)
                }
            }
        })

        fabAdd.setOnClickListener{
            val intent = Intent(context, AddChangeCategoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCategoryClick(costProfitCategory: CostProfitCategory) {
        val intent = Intent(context, AddChangeCategoryActivity::class.java)
        intent.putExtra("action", "edit")
        intent.putExtra("name", costProfitCategory.name)
        intent.putExtra("iconResource", costProfitCategory.iconResource)
        intent.putExtra("id", costProfitCategory.id)
        startActivity(intent)
    }

    override fun onCategoryLongClick(costProfitCategory: CostProfitCategory) {
        vmCategories.deleteCategory(costProfitCategory)
        Toast.makeText(context, "Категория удалена", Toast.LENGTH_LONG).show()
    }
}
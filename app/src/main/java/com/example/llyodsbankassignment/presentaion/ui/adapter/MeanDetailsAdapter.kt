package com.example.llyodsbankassignment.presentaion.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.llyodsbankassignment.R
import com.example.llyodsbankassignment.databinding.ItemCategoryListBinding
import com.project.domain.models.models.Meals
import com.project.utils.loadWithException

class MeanDetailsAdapter() : RecyclerView.Adapter<MeanDetailsAdapter.CategoryDataViewHolder>() {

    var categoryList = ArrayList<Meals>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryDataViewHolder {
        return CategoryDataViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_category_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryDataViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class CategoryDataViewHolder(val binding: ItemCategoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
            binding.catTitle.text = categoryList.get(position).strMeal
            binding.catImg.loadWithException(
                categoryList.get(position).strMealThumb,
                R.drawable.ic_launcher_foreground
            )
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun updateList(catList: ArrayList<Meals>) {
        this.categoryList = catList
        notifyDataSetChanged()
    }
}
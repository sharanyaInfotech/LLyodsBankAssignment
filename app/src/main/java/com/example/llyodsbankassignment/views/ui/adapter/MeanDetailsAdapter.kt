package com.example.llyodsbankassignment.views.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.llyodsbankassignment.R
import com.example.llyodsbankassignment.databinding.ItemCategoryListBinding
import com.example.llyodsbankassignment.domain.models.Meals
import com.example.llyodsbankassignment.utils.loadWithException
import com.example.llyodsbankassignment.utils.showOrGone

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
            binding.imgRgtArrow.showOrGone(false)
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
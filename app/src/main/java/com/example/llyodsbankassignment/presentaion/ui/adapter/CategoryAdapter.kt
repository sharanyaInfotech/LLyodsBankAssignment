package com.example.llyodsbankassignment.presentaion.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.llyodsbankassignment.R
import com.example.llyodsbankassignment.databinding.ItemCategoryListBinding
import com.example.llyodsbankassignment.presentaion.ui.listners.OnItemClickListner
import com.project.domain.models.models.Categories
import com.project.utils.loadWithException

class CategoryAdapter(private val listner: OnItemClickListner) :
    RecyclerView.Adapter<CategoryAdapter.CategoryDataViewHolder>() {

    var categoryList = ArrayList<Categories>()

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
            binding.catTitle.text = categoryList.get(position).strCategory
            binding.catImg.loadWithException(
                categoryList.get(position).strCategoryThumb,
                R.drawable.ic_launcher_foreground
            )
            binding.categoryDataContainer.setOnClickListener(View.OnClickListener {
                listner.onItemSelected(categoryList.get(position), position)
            })

        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun updateList(catList: ArrayList<Categories>) {
        this.categoryList = catList
        notifyDataSetChanged()
    }
}
package com.example.llyodsbankassignment.data.mapper

import com.example.llyodsbankassignment.domain.models.Categories
import com.example.llyodsbankassignment.domain.models.CategoryResponse
import com.example.llyodsbankassignment.domain.models.MealByCategoryModel
import com.example.llyodsbankassignment.domain.models.Meals
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesDetailMapper @Inject constructor() {
    fun toShareData(categoryFromServer: List<Categories>): List<Categories> {
        return categoryFromServer
    }

    fun MapMealData(mealData: List<Meals>) : List<Meals>{
        return mealData
    }
}

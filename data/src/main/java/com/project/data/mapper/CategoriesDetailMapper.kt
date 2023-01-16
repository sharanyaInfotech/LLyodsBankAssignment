package com.project.data.mapper

import com.project.domain.models.models.CategoryResponse
import com.project.domain.models.models.MealByCategoryModel
import javax.inject.Inject

class CategoriesDetailMapper @Inject constructor() {
    fun toShareData(categoryFromServer: CategoryResponse): CategoryResponse {
        return  categoryFromServer
    }

    fun MapMealData(mealData: MealByCategoryModel) : MealByCategoryModel{
        return mealData
    }
}

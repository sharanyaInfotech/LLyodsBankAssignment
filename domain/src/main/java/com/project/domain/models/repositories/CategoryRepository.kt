package com.project.domain.models.repositories

import com.project.domain.models.models.CategoryResponse
import com.project.domain.models.models.MealByCategoryModel

interface CategoryRepository {
     suspend fun fetchCategories(): CategoryResponse
     suspend fun fetchMealByCategory(catStr: String): MealByCategoryModel
}
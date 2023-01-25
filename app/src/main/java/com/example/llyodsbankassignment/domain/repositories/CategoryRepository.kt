package com.example.llyodsbankassignment.domain.repositories

import com.example.llyodsbankassignment.domain.models.CategoryResponse
import com.example.llyodsbankassignment.domain.models.MealByCategoryModel

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
     fun fetchCategories(): Flow<CategoryResponse>
     fun fetchMealByCategory(catStr: String): Flow<MealByCategoryModel>
}
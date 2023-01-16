package com.project.data.repo

import com.project.data.APIs.ApiService
import com.project.data.mapper.CategoriesDetailMapper
import com.project.domain.models.models.CategoryResponse
import com.project.domain.models.models.MealByCategoryModel
import com.project.domain.models.repositories.CategoryRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val categoriesDetailMapper: CategoriesDetailMapper): CategoryRepository{
    override suspend fun fetchCategories(): CategoryResponse {
        return categoriesDetailMapper.toShareData(apiService.getCategoryList())
    }

    override suspend fun fetchMealByCategory(catStr: String): MealByCategoryModel {
         return categoriesDetailMapper.MapMealData(apiService.getMealByCategory(catStr))
    }

}
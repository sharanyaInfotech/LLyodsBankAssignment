package com.project.data.APIs

import com.project.domain.models.models.CategoryResponse
import com.project.domain.models.models.MealByCategoryModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
     @GET("categories.php")
     suspend fun getCategoryList(): CategoryResponse

     @GET("filter.php")
     suspend fun getMealByCategory(@Query("c") category: String): MealByCategoryModel

}
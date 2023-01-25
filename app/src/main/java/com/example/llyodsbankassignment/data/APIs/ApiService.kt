package com.example.llyodsbankassignment.data.APIs

import com.example.llyodsbankassignment.domain.models.CategoryResponse
import com.example.llyodsbankassignment.domain.models.MealByCategoryModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
     @GET("categories.php")
     suspend fun getCategoryList(): CategoryResponse

     @GET("filter.php")
     suspend fun getMealByCategory(@Query("c") category: String): MealByCategoryModel

}
package com.example.llyodsbankassignment.data.repo

import com.example.llyodsbankassignment.data.APIs.ApiService
import com.example.llyodsbankassignment.data.mapper.CategoriesDetailMapper
import com.example.llyodsbankassignment.data.models.CategoriesDTO
import com.example.llyodsbankassignment.data.models.CategoryResponseDTO
import com.example.llyodsbankassignment.data.models.MealsDTO
import com.example.llyodsbankassignment.domain.models.Categories
import com.example.llyodsbankassignment.domain.models.CategoryResponse
import com.example.llyodsbankassignment.domain.models.MealByCategoryModel
import com.example.llyodsbankassignment.domain.models.Meals
import com.example.llyodsbankassignment.domain.repositories.CategoryRepository
import com.example.llyodsbankassignment.utils.NetworkAvailable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class CatRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val categoriesDetailMapper: CategoriesDetailMapper,
    @Named("ioDispatcher") val dispatcher: CoroutineDispatcher,
    val networkAvailable: NetworkAvailable): CategoryRepository{

    override fun fetchCategories(): Flow<CategoryResponse> =
        flow<CategoryResponse> {
            if (networkAvailable.isConnectedToNetwork()) {
                apiService.getCategoryList().also { _list->
                    emit(apiService.getCategoryList())
                }
            }
        }.flowOn(dispatcher)


    override fun fetchMealByCategory(catStr: String): Flow<MealByCategoryModel> =
        flow<MealByCategoryModel>{
            if (networkAvailable.isConnectedToNetwork()) {
                apiService.getMealByCategory(catStr).also { _list->
                    emit(apiService.getMealByCategory(catStr))
                }
            }
    }.flowOn(dispatcher)


}
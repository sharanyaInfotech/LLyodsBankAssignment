package com.example.llyodsbankassignment.domain.usecases

import com.example.llyodsbankassignment.data.models.MealsDTO
import com.example.llyodsbankassignment.domain.models.MealByCategoryModel
import com.example.llyodsbankassignment.domain.models.Meals
import com.example.llyodsbankassignment.domain.repositories.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class GetMealUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
     operator fun invoke(catStr: String): Flow<MealByCategoryModel> {
        return categoryRepository.fetchMealByCategory(catStr)
    }
}
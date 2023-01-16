package com.project.domain.models.usecases

import com.project.domain.models.models.MealByCategoryModel
import com.project.domain.models.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMealUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    operator fun invoke(catStr: String): Flow<MealByCategoryModel> {
        return flow {
            emit(categoryRepository.fetchMealByCategory(catStr))
        }
    }
}
package com.project.domain.models.usecases

import com.project.domain.models.models.CategoryResponse
import com.project.domain.models.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryUseCase @Inject constructor(private val catRepoImpl: CategoryRepository) {
     operator fun invoke(): Flow<CategoryResponse> {
         return flow {
             emit(catRepoImpl.fetchCategories())
         }
    }
}
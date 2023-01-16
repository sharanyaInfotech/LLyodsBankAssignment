package com.project.domain.models.usecases

import com.project.domain.models.models.CategoryResponse
import com.project.domain.models.repositories.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class CategoryUseCase @Inject constructor(private val catRepoImpl: CategoryRepository,
                                          @Named("ioDispatcher") val dispatcher: CoroutineDispatcher) {
     operator fun invoke(): Flow<CategoryResponse> {
         return flow {
             emit(catRepoImpl.fetchCategories())
         }.flowOn(dispatcher)
    }
}
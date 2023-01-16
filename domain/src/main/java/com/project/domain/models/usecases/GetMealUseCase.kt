package com.project.domain.models.usecases

import com.project.domain.models.models.MealByCategoryModel
import com.project.domain.models.repositories.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class GetMealUseCase @Inject constructor(private val categoryRepository: CategoryRepository,
                                         @Named("ioDispatcher") val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(catStr: String): Flow<MealByCategoryModel> {
        return flow {
            emit(categoryRepository.fetchMealByCategory(catStr))
        }.flowOn(dispatcher)
    }
}
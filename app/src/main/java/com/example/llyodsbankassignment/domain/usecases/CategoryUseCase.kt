package com.example.llyodsbankassignment.domain.usecases

import com.example.llyodsbankassignment.domain.models.CategoryResponse
import com.example.llyodsbankassignment.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
      operator fun invoke(): Flow<CategoryResponse> {
         return categoryRepository.fetchCategories()
    }
}
package com.project.di

import com.project.data.APIs.ApiService
import com.project.data.mapper.CategoriesDetailMapper
import com.project.data.repo.CatRepositoryImpl
import com.project.domain.models.repositories.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepoModule {

@Provides
fun provideRepository(apiService: ApiService, categoriesDetailMapper: CategoriesDetailMapper): CategoryRepository{
    return CatRepositoryImpl(apiService, categoriesDetailMapper)
}
}
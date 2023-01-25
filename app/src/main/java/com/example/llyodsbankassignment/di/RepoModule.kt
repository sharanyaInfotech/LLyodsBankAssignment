package com.example.llyodsbankassignment.di

import com.example.llyodsbankassignment.data.APIs.ApiService
import com.example.llyodsbankassignment.data.mapper.CategoriesDetailMapper
import com.example.llyodsbankassignment.data.repo.CatRepositoryImpl
import com.example.llyodsbankassignment.domain.repositories.CategoryRepository
import com.example.llyodsbankassignment.utils.NetworkAvailable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
class RepoModule {

    @Provides
    fun provideRepository(
        apiService: ApiService,
        categoriesDetailMapper: CategoriesDetailMapper,
        @Named("ioDispatcher")
        dispatcher: CoroutineDispatcher,
        isNetworkAvailable: NetworkAvailable
    ): CategoryRepository =
        CatRepositoryImpl(apiService,categoriesDetailMapper, dispatcher, isNetworkAvailable)
}
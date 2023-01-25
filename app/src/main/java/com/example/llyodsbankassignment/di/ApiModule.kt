package com.example.llyodsbankassignment.di

import com.example.llyodsbankassignment.data.APIs.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
class ApiModule {
    @Provides
    fun bindApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
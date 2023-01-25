package com.example.llyodsbankassignment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
    @Provides
    @Named("ioDispatcher")
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
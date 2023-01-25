package com.project.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.llyodsbankassignment.domain.repositories.CategoryRepository
import com.example.llyodsbankassignment.domain.usecases.GetMealUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)

class GetMealUseCaseTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var getMealUseCase: GetMealUseCase

    @Mock
    lateinit var catRepository: CategoryRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getMealUseCase = GetMealUseCase(catRepository)
    }

    @Test
    fun getAnimesTest() = runBlockingTest {
        getMealUseCase.invoke("vegan")
        Mockito.verify(catRepository, Mockito.times(1)).fetchMealByCategory("vegan")
    }

}
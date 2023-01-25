package com.project.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.llyodsbankassignment.domain.repositories.CategoryRepository
import com.example.llyodsbankassignment.domain.usecases.CategoryUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class GetCategoryListUseCaseTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var getCategoryListUseCase: CategoryUseCase
    @Mock
    lateinit var categoryRepository: CategoryRepository

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        getCategoryListUseCase= CategoryUseCase(categoryRepository)
    }

    @Test
    fun `get categories`()= runBlockingTest{
        getCategoryListUseCase.invoke()
        Mockito.verify(categoryRepository,Mockito.times(1)).fetchCategories()
    }

}
package com.example.llyodsbankassignment.viwemodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.llyodsbankassignment.domain.models.Categories
import com.example.llyodsbankassignment.domain.models.CategoryResponse
import com.example.llyodsbankassignment.views.ui.viewmodels.CategoryViewModel
import com.example.llyodsbankassignment.domain.usecases.CategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class CategoryViewModelTest {

    private lateinit var catViewModel: CategoryViewModel
    @Mock
    private lateinit var categoryUseCase: CategoryUseCase

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        catViewModel = CategoryViewModel(categoryUseCase)
        categoryUseCase = catViewModel.getCategoryUseCase
    }

    @Test
    fun handleOperationSuccessTest(): Unit = runTest {
        Mockito.`when`(categoryUseCase.invoke()).thenReturn(
            flowOf( CategoryResponse(
                arrayListOf(
                    Categories("1","abcd", "https://", "dhjvchdjbcv"),
                ),)
            )
        )

        val catList=categoryUseCase.invoke().flatMapConcat { it.categories.asFlow()}.toList()
        Assert.assertNotEquals(catList.size, 0)
        Assert.assertEquals(catList.get(0).strCategory, "abcd")
    }

    @Test(expected = retrofit2.HttpException::class)
    fun handleOperationFailTest(): Unit = runTest {
        Mockito.`when`(categoryUseCase).thenThrow(
            HttpException(
                Response.error<Any>(
                    409,
                    ResponseBody.create("plain/text".toMediaTypeOrNull(), "")
                )
            )
        )
        Assert.assertEquals(categoryUseCase.invoke().flatMapConcat{ it.categories.asFlow()}.toList().size, 0)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
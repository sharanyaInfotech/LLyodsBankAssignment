package com.example.llyodsbankassignment.viwemodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.llyodsbankassignment.presentaion.ui.viewmodels.CategoryViewModel
import com.project.domain.models.models.Categories
import com.project.domain.models.models.CategoryResponse
import com.project.domain.models.usecases.CategoryUseCase
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class CategoryViewModelTest {

    @MockK
    private lateinit var catViewModel: CategoryViewModel
    private val categoryUseCase = mockk<CategoryUseCase>(relaxed = true)
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        catViewModel = CategoryViewModel(categoryUseCase)
        catViewModel.getCategoryUseCase = categoryUseCase
    }

    @Test
    fun handleOperationSuccessTest(): Unit = runTest {
        val mcategories = CategoryResponse(arrayListOf(
            Categories("1", "Vegan","https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/qxutws1486978099.jpg","abcd")))
        Mockito.`when`(categoryUseCase()).thenReturn(flowOf(mcategories))
        val categoryList=(categoryUseCase()).flatMapConcat { mcategories.categories.asFlow()}.toList()
        Assert.assertNotEquals(categoryList.size, 0)
        Assert.assertEquals(categoryList.get(0).idCategory, "1")
    }

    @Test(expected = retrofit2.HttpException::class)
    fun handleOperationFailTest(): Unit = runTest {
        Mockito.`when`(categoryUseCase()).thenThrow(
            HttpException(
                Response.error<Any>(
                    409,
                    ResponseBody.create("plain/text".toMediaTypeOrNull(), "")
                )
            )
        )
        Assert.assertEquals(categoryUseCase().flatMapConcat { it.categories.asFlow()}.toList().size, 0)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
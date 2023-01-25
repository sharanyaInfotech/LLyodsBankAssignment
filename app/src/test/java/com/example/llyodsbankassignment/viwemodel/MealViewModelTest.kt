package com.example.llyodsbankassignment.viwemodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.llyodsbankassignment.domain.models.MealByCategoryModel
import com.example.llyodsbankassignment.domain.models.Meals
import com.example.llyodsbankassignment.domain.usecases.GetMealUseCase
import com.example.llyodsbankassignment.views.ui.viewmodels.MealDetailViewModel
import kotlinx.coroutines.Dispatchers
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

class MealViewModelTest {

    @Mock
    private lateinit var getMealUseCase: GetMealUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()
    private lateinit var mealDetailViewModel: MealDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        mealDetailViewModel = MealDetailViewModel(getMealUseCase)
        mealDetailViewModel.getMealUseCase = getMealUseCase
    }

    @Test(expected = retrofit2.HttpException::class)
    fun handleOperationFailTest(): Unit = runTest(dispatcher) {
        Mockito.`when`((getMealUseCase).invoke("vegan")).thenThrow(
            HttpException(
                Response.error<Any>(
                    409,
                    ResponseBody.create("plain/text".toMediaTypeOrNull(), "")
                )
            )
        )
        Assert.assertEquals(getMealUseCase.invoke("vegan").flatMapConcat { it.meals.asFlow()}.toList().size, 0)
    }

    @Test
    fun handleOperationSuccessTest(): Unit = runTest(dispatcher) {
        Mockito.`when`(getMealUseCase.invoke("vegan")).thenReturn(
            flowOf( MealByCategoryModel(
                arrayListOf(
                    Meals("abcd","","1"),
                ),))
        )

        val mealList=getMealUseCase.invoke("vegan").flatMapConcat { it.meals.asFlow()}.toList()
        Assert.assertNotEquals(mealList.size, 0)
        Assert.assertEquals(mealList.get(0).idMeal, "1")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
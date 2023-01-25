package com.example.llyodsbankassignment.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.llyodsbankassignment.data.mapper.CategoriesDetailMapper
import com.example.llyodsbankassignment.data.models.MealsDTO
import com.example.llyodsbankassignment.data.repo.CatRepositoryImpl
import com.example.llyodsbankassignment.utils.NetworkAvailable
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class MealRepositoryImplTest {
    private var mealsDto= listOf(
        MealsDTO("abcd", "https://","2"),)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()
    lateinit var mockWebServer:MockWebServer
    lateinit var apiService: com.example.llyodsbankassignment.data.APIs.ApiService
    lateinit var forecastRepositoryImpl: CatRepositoryImpl
    
    @Mock
    lateinit var mapper: CategoriesDetailMapper
    @Mock
    lateinit var networkAvailable: NetworkAvailable
    
    @Before
    fun setUp(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.example.llyodsbankassignment.data.APIs.ApiService::class.java)
        forecastRepositoryImpl = CatRepositoryImpl(apiService, mapper,dispatcher,networkAvailable)
    }

    @Test
    fun `get Employees from api using flow status success`() = runTest {

        Mockito.`when`(networkAvailable.isConnectedToNetwork()).thenReturn(true)
       `get anime quotes with http code 200`()
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `get Employees from api using flow status failed`() = runTest {
        Mockito.`when`(networkAvailable.isConnectedToNetwork()).thenReturn(true)
        `get anime quotes with http code 404`()
    }
    @Test(expected = retrofit2.HttpException::class)
    fun `get anime quotes with http code 404`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = apiService.getMealByCategory("vegan")
        Assert.assertEquals(actualResponse.meals.size, 0)
    }

    @Test
    fun `get anime quotes with http code 200`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(mealsDto))
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = apiService.getMealByCategory("vegan")
        Assert.assertEquals(actualResponse.meals.get(0).strMeal, "abcd")
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }

}
package com.example.llyodsbankassignment.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.llyodsbankassignment.data.mapper.CategoriesDetailMapper
import com.example.llyodsbankassignment.data.repo.CatRepositoryImpl
import com.example.llyodsbankassignment.domain.models.Categories
import com.example.llyodsbankassignment.utils.NetworkAvailable
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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


@OptIn(ExperimentalCoroutinesApi::class)
class CategoryRepositoryImplTest {
    private var categoryDTO= arrayListOf(
        Categories("23","vegan","https://", "abcdsjfdgdgkgtgnbfrtkjbjkbm "),)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()

    lateinit var citiesRepositoryImpl: CatRepositoryImpl

    @Mock
    lateinit var isNetworkAvailable: NetworkAvailable

    @Mock
    lateinit var mapper: CategoriesDetailMapper

    private lateinit var mockWebServer: MockWebServer
    lateinit var apiService: com.example.llyodsbankassignment.data.APIs.ApiService

    @Before
    fun setUp() {
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
        citiesRepositoryImpl = CatRepositoryImpl(apiService, mapper, dispatcher, isNetworkAvailable)
    }

    @Test
    fun `get Category from api using flow status success`() = runTest {

        Mockito.`when`(isNetworkAvailable.isConnectedToNetwork()).thenReturn(true)
        `get categories with http code 200`()
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `get Category from api using flow status failed`() = runTest {
        Mockito.`when`(isNetworkAvailable.isConnectedToNetwork()).thenReturn(true)
        `get categories with http code 400`()
    }


    @Test
    fun `get categories with http code 200`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(flowOf(categoryDTO)))
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = apiService.getCategoryList()
        Assert.assertEquals(actualResponse.categories.get(0).strCategory, "vegan")
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `get categories with http code 400`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(Gson().toJson(null))
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = apiService.getCategoryList()
        Assert.assertEquals(actualResponse.categories.size, 0)

    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }
}
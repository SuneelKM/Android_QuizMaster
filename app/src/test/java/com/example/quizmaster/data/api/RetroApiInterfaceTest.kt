package com.example.quizmaster.data.api

import com.example.quizmaster.data.model.OpentdbAPI.Questions
import com.google.gson.Gson
import io.reactivex.rxjava3.kotlin.subscribeBy
import junit.framework.TestCase
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class RetroApiInterfaceTest{
    lateinit var inter: RetroApiInterface
    lateinit var mockServer: MockWebServer
    lateinit var gson: Gson



    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockServer = MockWebServer()
        gson = Gson()
        inter = Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(RetroApiInterface::class.java)
    }

    @Test
    fun getQuestionsTest() {

        val mockRes = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
        mockServer.enqueue(mockRes)

        val result = inter.getQuestions("0", "0", "0", "0")

        result.subscribeBy{
            it
        }
        val req = mockServer.takeRequest()
        assertEquals("/api.php?amount=0&category=0&difficulty=0&type=0", req.path)
    }


    @After
    fun destroy() {
        mockServer.shutdown()
    }


}

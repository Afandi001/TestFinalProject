package com.example.finalproject.retrofit
import android.content.Context
import com.example.finalproject.App
import com.example.finalproject.utils.Constants
import com.example.finalproject.utils.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val authToken = SessionManager(App.instance.applicationContext).userToken
    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            chain.proceed(chain.request().newBuilder().also {
                if (authToken != null) {
                    it.addHeader("Authorization", authToken).build()
                }
            }.build())
        }.addInterceptor(logging).build()

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api by lazy {
        retrofit.create(ApiService::class.java)
    }
}
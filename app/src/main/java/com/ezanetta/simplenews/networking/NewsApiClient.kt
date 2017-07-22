package com.ezanetta.simplenews.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiClient {

    val newsApi: NewsApi

    init {
        val httpClient = OkHttpClient.Builder()

        setupLoggingInterceptor(httpClient)
        setupApiKeyInterceptor(httpClient)

        val client = Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

        newsApi = client.create(NewsApi::class.java)
    }

    private fun setupApiKeyInterceptor(httpClient: OkHttpClient.Builder) {
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", "09138c03f9df449785d670d94b2d80b3")
                    .build()

            val requestBuilder = original.newBuilder()
                    .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    private fun setupLoggingInterceptor(httpClient: OkHttpClient.Builder) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)
    }
}
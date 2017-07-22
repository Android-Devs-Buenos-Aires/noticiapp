package com.ezanetta.simplenews.networking

import com.ezanetta.simplenews.domain.responses.ArticlesResponse
import com.ezanetta.simplenews.domain.responses.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v1/articles")
    fun getNews(@Query("source") source: String,
                @Query("sortBy") sortBy: String): Call<ArticlesResponse>

    @GET("/v1/sources")
    fun getSources(@Query("language") string: String = "en"): Call<SourcesResponse>
}
package com.ezanetta.simplenews.networking

import com.ezanetta.simplenews.domain.responses.ArticlesResponse
import com.ezanetta.simplenews.domain.responses.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines")
    fun getNews(@Query("sources") source: String): Call<ArticlesResponse>

    @GET("/v2/sources")
    fun getSources(@Query("language") string: String = "en"): Call<SourcesResponse>
}
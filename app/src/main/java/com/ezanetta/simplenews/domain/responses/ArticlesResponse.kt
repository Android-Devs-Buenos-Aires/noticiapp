package com.ezanetta.simplenews.domain.responses

import com.ezanetta.simplenews.domain.model.Article
import com.google.gson.annotations.SerializedName

class ArticlesResponse(@SerializedName("source") val source: String,
                       @SerializedName("articles") val articles: List<Article>)
package com.example.newz.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopArticlesResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "section")
    val section: String,
    @Json(name = "num_results")
    val count: Int,
    @Json(name = "results")
    val articles: List<Article>,
    @Json(name = "last_updated")
    val lastUpdated: String,
    @Json(name = "copyright")
    val copyright: String
)


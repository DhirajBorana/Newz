package com.example.newz.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Article(
    @Json(name = "uri")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "abstract")
    val description: String,
    @Json(name = "section")
    val section: String,
    @Json(name = "subsection")
    val subsection: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "short_url")
    val shortUrl: String,
    @Json(name = "byline")
    val byline: String,
    @Json(name = "multimedia")
    val multimedia: List<Multimedia>,
    @Json(name = "published_date")
    val publishedDate: String
)

@JsonClass(generateAdapter = true)
data class Multimedia(
    @Json(name = "url")
    val url: String,
    @Json(name = "format")
    val format: String,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "type")
    val type: String,
    @Json(name = "caption")
    val caption: String,
    @Json(name = "copyright")
    val copyright: String
)
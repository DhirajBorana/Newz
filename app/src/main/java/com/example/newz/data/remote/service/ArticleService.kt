package com.example.newz.data.remote.service

import com.example.newz.BuildConfig
import com.example.newz.data.model.TopArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleService {

    @GET("/svc/topstories/v2/{section}.json?api-key=${BuildConfig.NY_TIMES_API_KEY}")
    suspend fun getTopStories(
        @Path("section") section: String
    ) : TopArticlesResponse
}
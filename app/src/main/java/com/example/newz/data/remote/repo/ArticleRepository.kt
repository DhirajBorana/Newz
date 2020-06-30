package com.example.newz.data.remote.repo

import com.example.newz.data.model.TopArticlesResponse
import com.example.newz.data.remote.ResponseHandler
import com.example.newz.data.remote.Result
import com.example.newz.data.remote.service.ArticleService
import java.lang.Exception

class ArticleRepository(private val articleService: ArticleService) {

    private val responseHandler = ResponseHandler()

    suspend fun getTopArticles(section: String): Result<TopArticlesResponse> {
        return try {
            responseHandler.handleSuccess(articleService.getTopStories(section))
        } catch (e: Exception) {
            responseHandler.handleError(e)
        }
    }
}
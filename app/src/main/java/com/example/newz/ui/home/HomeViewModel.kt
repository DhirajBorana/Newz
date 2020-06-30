package com.example.newz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newz.data.model.TopArticlesResponse
import com.example.newz.data.remote.ApiFactory
import com.example.newz.data.remote.Result
import com.example.newz.data.remote.repo.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repo: ArticleRepository
    private var selectedSection: String = ""
    private var job: Job? = null

    private val _result = MutableLiveData<Result<TopArticlesResponse>>()
    val result: LiveData<Result<TopArticlesResponse>>
        get() = _result

    init {
        val articleService = ApiFactory.articleService
        repo = ArticleRepository(articleService)
    }

    fun setSection(section: String) {
        if (section == selectedSection) return
        selectedSection = section
        loadTopArticles()
    }

    private fun loadTopArticles() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.Main) {
            _result.value = Result.loading(null)
            _result.value = repo.getTopArticles(selectedSection)
        }
    }

    fun retry() {
        loadTopArticles()
    }
}
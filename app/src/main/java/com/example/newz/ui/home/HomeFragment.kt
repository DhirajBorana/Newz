package com.example.newz.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newz.R
import com.example.newz.data.model.Article
import com.example.newz.data.remote.ErrorType
import com.example.newz.data.remote.Status
import com.example.newz.databinding.FragmentHomeBinding
import com.example.newz.ui.articleoptions.ArticleOptionsDialogFragment
import com.example.newz.ui.section.SectionModel
import com.example.newz.utils.exhaustive
import com.example.newz.utils.removeSpace
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback
import java.util.*


class HomeFragment : Fragment(), ArticleAdapter.ArticleListener {

    private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ArticleAdapter
    private val pass: Unit = Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        initArticleList()

        SectionModel.checked.observe(viewLifecycleOwner, Observer {
            val section = getString(it.titleRes).toLowerCase(Locale.getDefault()).removeSpace()
            binding.section = getString(it.titleRes)
            viewModel.setSection(section)
        })

        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    val articles = result.data?.articles
                    adapter.submitList(articles)
                }
                Status.ERROR -> showError(result.error)
                Status.LOADING -> adapter.submitList(emptyList())
            }.exhaustive()
        })

        binding.layoutError.btnRetry.setOnClickListener { viewModel.retry() }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.retry()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initArticleList() {
        adapter = ArticleAdapter(this)
        binding.articleList.adapter = adapter
    }

    private fun showError(error: ErrorType?) {
        error?.let {
            when (it) {
                ErrorType.NetworkError -> {
                    binding.layoutError.apply {
                        image.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_offline
                            )
                        )
                        message.text = getString(R.string.message_error_offline)
                        instruction.text = getString(R.string.instruction_error_offline)
                    }
                }
                is ErrorType.GenericError -> {
                    binding.layoutError.apply {
                        image.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_error
                            )
                        )
                        message.text = getString(R.string.message_error_something_went_wrong)
                        instruction.text =
                            getString(R.string.instruction_error_something_went_wrong)
                    }
                }
            }.exhaustive()
        }
    }

    override fun onArticleClicked(article: Article) {
        launchCustomTab(article.url)
    }

    private fun launchCustomTab(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .addDefaultShareMenuItem()
            .setShowTitle(true)
            .build()

        CustomTabsHelper.addKeepAliveExtra(requireContext(), customTabsIntent.intent)
        CustomTabsHelper.openCustomTab(
            requireContext(), customTabsIntent, Uri.parse(url), WebViewFallback()
        )
    }

    override fun onArticleLongClicked(article: Article) {
        val articleOptionsDialog = ArticleOptionsDialogFragment(article)
        articleOptionsDialog.show(requireActivity().supportFragmentManager, articleOptionsDialog.tag)
    }
}
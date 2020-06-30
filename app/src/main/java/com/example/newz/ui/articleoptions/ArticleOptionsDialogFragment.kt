package com.example.newz.ui.articleoptions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.newz.R
import com.example.newz.data.model.Article
import com.example.newz.databinding.DialogArticleOptionsBinding
import com.example.newz.utils.applyWindowInsets
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ArticleOptionsDialogFragment(private val article: Article) : BottomSheetDialogFragment() {

    private lateinit var binding: DialogArticleOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DialogArticleOptionsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.applyWindowInsets()
        binding.article = article

        binding.options.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_open_in_browser -> openArticleInBrowser()
                R.id.menu_share -> shareArticle()
                else -> false
            }
        }
    }

    private fun openArticleInBrowser(): Boolean {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
        startActivity(browserIntent)
        dialog?.cancel()
        return true
    }

    private fun shareArticle(): Boolean {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, article.url)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, null))
        dialog?.cancel()
        return true
    }
}
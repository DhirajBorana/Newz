package com.example.newz.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newz.data.model.Article
import com.example.newz.databinding.ItemArticleBinding

class ArticleAdapter(private val listener: ArticleListener) :
    ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ArticleViewHolder private constructor(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article, listener: ArticleListener) {
            binding.apply {
                article = item
                root.setOnClickListener { listener.onArticleClicked(item) }
                root.setOnLongClickListener {
                    listener.onArticleLongClicked(item)
                    true
                }
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ArticleViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemArticleBinding.inflate(layoutInflater, parent, false)
                return ArticleViewHolder(binding)
            }
        }
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    interface ArticleListener {
        fun onArticleClicked(article: Article)
        fun onArticleLongClicked(article: Article)
    }
}

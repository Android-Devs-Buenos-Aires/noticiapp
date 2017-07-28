package com.ezanetta.simplenews.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ezanetta.simplenews.R
import com.ezanetta.simplenews.domain.model.Article
import com.ezanetta.simplenews.ui.adapters.holders.ArticleViewHolder
import java.util.*

class ArticlesAdapter(val listener: (Article) -> Unit) : RecyclerView.Adapter<ArticleViewHolder>() {

    private var articles: List<Article> = ArrayList()

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        with(articles[position]) {
            holder.bindArticle(this)
            holder.itemView.setOnClickListener { listener(this) }
        }
    }

    override fun getItemCount() = articles.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)

        return ArticleViewHolder(view)
    }

    fun loadArticles(articles: List<Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    fun cleanArticles() {
        articles = ArrayList()
        notifyDataSetChanged()
    }
}
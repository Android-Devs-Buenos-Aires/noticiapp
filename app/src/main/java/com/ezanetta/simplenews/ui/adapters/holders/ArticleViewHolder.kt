package com.ezanetta.simplenews.ui.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ezanetta.simplenews.R
import com.ezanetta.simplenews.domain.model.Article
import com.ezanetta.simplenews.utils.extensions.loadUrl
import com.ezanetta.simplenews.utils.extensions.toDateWithFormat

class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var image: ImageView = view.findViewById(R.id.image)
    private var title: TextView = view.findViewById(R.id.title)
    private var description: TextView = view.findViewById(R.id.description)
    private var date: TextView = view.findViewById(R.id.date)

    fun bindArticle(article: Article) {
        image.loadUrl(article.image)
        title.text = article.title
        description.text = article.description
        date.text = article.publishedAt?.toDateWithFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", "MMM dd")
    }
}
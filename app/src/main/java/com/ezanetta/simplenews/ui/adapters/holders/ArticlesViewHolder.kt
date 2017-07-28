package com.ezanetta.simplenews.ui.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ezanetta.simplenews.R
import com.ezanetta.simplenews.domain.model.Article
import com.ezanetta.simplenews.utils.extensions.loadUrl
import com.ezanetta.simplenews.utils.extensions.toDateWithFormat

/**
 * Created by rmuhamed on 28/7/17.
 */
class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var image: ImageView = view.findViewById(R.id.image) as ImageView
    private var title: TextView = view.findViewById(R.id.title) as TextView
    private var description: TextView = view.findViewById(R.id.description) as TextView
    private var date: TextView = view.findViewById(R.id.date) as TextView

    fun bindArticle(article: Article) {
        image.loadUrl(article.image)
        title.text = article.title
        description.text = article.description
        date.text = article.publishedAt?.toDateWithFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", "MMM dd")
    }
}
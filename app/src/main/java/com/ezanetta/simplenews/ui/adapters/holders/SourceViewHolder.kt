package com.ezanetta.simplenews.ui.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ezanetta.simplenews.R
import com.ezanetta.simplenews.domain.model.Source
import com.ezanetta.simplenews.utils.extensions.loadUrl

class SourceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val image: ImageView = view.findViewById(R.id.image)
    private val title: TextView = view.findViewById(R.id.title)

    fun bindSource(source: Source) {
        image.loadUrl(source.getLogoImage())
        title.text = source.name
    }
}
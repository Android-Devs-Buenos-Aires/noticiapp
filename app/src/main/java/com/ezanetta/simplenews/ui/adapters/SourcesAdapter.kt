package com.ezanetta.simplenews.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ezanetta.simplenews.R
import com.ezanetta.simplenews.domain.model.Source
import com.ezanetta.simplenews.utils.extensions.loadUrl

class SourcesAdapter(val sources: List<Source>,
                     val listener: (Source) -> Unit) :
        RecyclerView.Adapter<SourcesAdapter.SourceViewHolder>() {

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        with(sources[position]) {
            holder.bindSource(this)
            holder.itemView.setOnClickListener { listener(this) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_source, parent, false)

        return SourceViewHolder(view)
    }

    override fun getItemCount() = sources.size

    class SourceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.findViewById(R.id.image) as ImageView
        private val title: TextView = view.findViewById(R.id.title) as TextView

        fun bindSource(source: Source) {
            image.loadUrl(source.getLogoImage())
            title.text = source.name
        }
    }
}
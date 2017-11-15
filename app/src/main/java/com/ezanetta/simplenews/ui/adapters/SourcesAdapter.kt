package com.ezanetta.simplenews.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ezanetta.simplenews.R
import com.ezanetta.simplenews.domain.model.Source
import com.ezanetta.simplenews.ui.adapters.holders.SourceViewHolder

class SourcesAdapter(private val sources: List<Source>,
                     private val listener: OnSourcesAction) :
        RecyclerView.Adapter<SourceViewHolder>() {

    interface OnSourcesAction {
        fun onClickSource(source: Source)
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        with(sources[position]) {
            holder.bindSource(this)
            holder.itemView.setOnClickListener { listener.onClickSource(this) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_source, parent, false)

        return SourceViewHolder(view)
    }

    override fun getItemCount() = sources.size
}
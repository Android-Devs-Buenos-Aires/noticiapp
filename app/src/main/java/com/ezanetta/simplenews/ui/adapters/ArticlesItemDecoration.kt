package com.ezanetta.simplenews.ui.adapters

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class ArticlesItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {

        when(parent.getChildAdapterPosition(view)) {
            0 -> setupRectFirst(outRect, space)
            else -> setupRectTopAndBottom(outRect, space)
        }
    }

    fun setupRectFirst(outRect: Rect, space: Int) {
        outRect.top = space
        outRect.bottom = space
    }

    fun setupRectTopAndBottom(outRect: Rect, space: Int) {
        outRect.bottom = space
    }
}
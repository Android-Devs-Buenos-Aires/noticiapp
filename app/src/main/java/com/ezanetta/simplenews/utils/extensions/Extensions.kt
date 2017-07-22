package com.ezanetta.simplenews.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ezanetta.simplenews.R
import java.text.SimpleDateFormat
import java.util.*


fun ImageView.loadUrl(url: String?, placeholder: Int = R.drawable.image_placeholder) {
    Glide.with(this.context).load(url).placeholder(placeholder).into(this)
}

fun String.toDateWithFormat(currentFormat: String, newFormat: String): String {
    val simpleFormat = SimpleDateFormat(currentFormat, Locale.getDefault())
    val newSimpleFormat = SimpleDateFormat(newFormat, Locale.getDefault())
    return newSimpleFormat.format(simpleFormat.parse(this))
}
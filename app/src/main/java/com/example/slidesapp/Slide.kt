package com.example.slidesapp

data class Slide(
    val title: String,
    val content: String,
    val backgroundColor: Int,
    val iconRes: Int = android.R.drawable.ic_menu_gallery,
    val actionText: String? = null,
    val actionListener: (() -> Unit)? = null
)
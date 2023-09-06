package com.noceli.diego.androidbookapi

data class Book(
    val title: String,
    val author: String,
    val description: String,
    val buyLink: String?,
    var isFavorite: Boolean = false
)
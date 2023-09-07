package com.noceli.diego.androidbookapi

import java.io.Serializable

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val description: String?,
    val buyLink: String?,
    val imageThumbnail: String?,
    var isFavorite: Boolean = false
) : Serializable
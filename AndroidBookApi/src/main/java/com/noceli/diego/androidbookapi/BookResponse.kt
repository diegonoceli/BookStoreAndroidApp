package com.noceli.diego.androidbookapi

import java.net.URL

data class BookResponse(
    val id: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo?,
    var isFavorite: Boolean = false
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val description: String?,
    val imageLinks: ImageLinks?
)

data class ImageLinks(
    val thumbnail: String
)

data class SaleInfo(
    val buyLink: String
)

fun BookResponse.toBook() = Book(
    id = id,
    title = volumeInfo.title,
    authors = volumeInfo.authors,
    buyLink = saleInfo?.buyLink,
    description = volumeInfo.description,
    imageThumbnail = volumeInfo.imageLinks?.thumbnail
)
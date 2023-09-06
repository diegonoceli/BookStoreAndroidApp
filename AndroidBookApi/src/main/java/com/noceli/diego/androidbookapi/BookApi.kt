package com.noceli.diego.androidbookapi

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookApi {
    companion object {
        private const val BASE_URL = "https://www.googleapis.com/books/v1/"
    }

    private val apiService: GoogleBooksApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(GoogleBooksApiService::class.java)
    }

    fun searchBooks(
        query: String,
        maxResults: Int,
        startIndex: Int,
        callback: Callback<BooksResponse<Book>>
    ) {
        apiService.searchBooks(query, maxResults, startIndex).enqueue(callback)
    }
}

package com.noceli.diego.androidbookapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookApi {
    companion object {
        const val BASE_URL = "https://www.googleapis.com/books/v1/"
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
        onResponse: (List<Book>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val callback = ApiResponseCallback<ListResponse<BookResponse>>()
        callback.onResponse { response ->
            response?.let { listResponse ->
                val books = listResponse.items.map { it.toBook() }
                onResponse(books)
            }
                ?: onResponse(emptyList())
        }
        callback.onError { error -> onError(error) }
        apiService.searchBooks(query, maxResults, startIndex).enqueue(callback)
    }
}

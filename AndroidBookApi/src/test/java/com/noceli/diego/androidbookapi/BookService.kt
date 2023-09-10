package com.noceli.diego.androidbookapi

import retrofit2.Call
import retrofit2.Retrofit

class BookService constructor(private val retrofit: Retrofit) :
    GoogleBooksApiService {
    private val endpoint by lazy { retrofit.create(GoogleBooksApiService::class.java) }
    override fun searchBooks(
        query: String,
        maxResults: Int,
        startIndex: Int
    ): Call<ListResponse<BookResponse>> =
        endpoint.searchBooks(query, maxResults, startIndex)
}

package com.noceli.diego.androidbookapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BookApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

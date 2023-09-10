package com.noceli.diego.androidbookapi

import org.junit.Test
import retrofit2.Retrofit

class RetrofitClientTest {
    @Test
    fun testRetrofitInstance() {
        val instance: Retrofit = RetrofitClient().retrofit
        assert(instance.baseUrl().toString() == BookApi.BASE_URL)
    }

    @Test
    fun testPlacesService() {
        val service = BookService(RetrofitClient().retrofit)

        val response = service.searchBooks("ios", 20, 0).execute()

        val errorBody = response.errorBody()
        assert(errorBody == null)

        val responseWrapper = response.body()
        assert(responseWrapper != null)
        assert(response.code() == 200)
    }
}

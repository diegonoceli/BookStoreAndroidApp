package com.noceli.diego.androidbookapi

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiResponseCallback<T> : Callback<T> {
    private var onResponseListener: ((response: T?) -> Unit)? = null
    private var onErrorListener: ((t: Throwable) -> Unit)? = null

    fun onResponse(onResponse: (response: T?) -> Unit) {
        onResponseListener = onResponse
    }

    fun onError(onError: (t: Throwable) -> Unit) {
        onErrorListener = onError
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onResponseListener?.invoke(response.body())
        } else {
            onErrorListener?.invoke(Throwable("API request failed with code ${response.code()}"))
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onErrorListener?.invoke(t)
    }
}

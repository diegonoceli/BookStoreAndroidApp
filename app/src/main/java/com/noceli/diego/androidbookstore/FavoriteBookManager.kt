package com.noceli.diego.androidbookstore

import android.content.Context
import android.content.SharedPreferences

class FavoriteBookManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "FavoriteBooks",
        Context.MODE_PRIVATE
    )

    fun isBookFavorite(bookTitle: String): Boolean {
        return sharedPreferences.getBoolean(bookTitle, false)
    }

    fun setBookFavorite(bookTitle: String, isFavorite: Boolean) {
        sharedPreferences.edit().putBoolean(bookTitle, isFavorite).apply()
    }
}
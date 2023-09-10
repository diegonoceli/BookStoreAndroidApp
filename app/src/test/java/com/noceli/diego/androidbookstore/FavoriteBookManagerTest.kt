package com.noceli.diego.androidbookstore

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FavoriteBookManagerTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var favoriteBookManager: FavoriteBookManager

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPreferences = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
        favoriteBookManager = FavoriteBookManager(context, sharedPreferences)
    }

    @Test
    fun testSetAndGetBookFavorite() {
        val bookTitle = "Test Book"
        assertFalse(favoriteBookManager.isBookFavorite(bookTitle))

        favoriteBookManager.setBookFavorite(bookTitle, true)
        assertTrue(favoriteBookManager.isBookFavorite(bookTitle))

        favoriteBookManager.setBookFavorite(bookTitle, false)
        assertFalse(favoriteBookManager.isBookFavorite(bookTitle))
    }
}

package com.noceli.diego.androidbookstore

import android.content.Intent
import android.os.Bundle
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.noceli.diego.androidbookapi.Book
import com.noceli.diego.androidbookapi.BookApi
import com.noceli.diego.androidbookstore.adapters.BooksAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var bookApi: BookApi
    private lateinit var itemListview: ListView
    private lateinit var adapter: BooksAdapter
    private lateinit var filterImageView: ImageView
    private lateinit var favoriteBookManager: FavoriteBookManager

    private val allBooks = mutableListOf<Book>()
    private val adapterBooks = mutableListOf<Book>()
    private var startIndex = 0
    private var showFavoritesOnly = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bookApi = BookApi()

        favoriteBookManager = FavoriteBookManager(this)
        itemListview = findViewById(R.id.itemListView)
        filterImageView = findViewById(R.id.filterIcon)
        adapter = BooksAdapter(adapterBooks, this)
        itemListview.adapter = adapter

        loadMoreBooks()

        itemListview.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}

            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount
                    && totalItemCount != 0 && !showFavoritesOnly
                ) {
                    startIndex = totalItemCount
                    loadMoreBooks()
                    adapter.notifyDataSetChanged()
                }
            }
        })

        itemListview.setOnItemClickListener { _, _, position, _ ->
            val selectedBook = allBooks[position]

            val intent = Intent(this, BookDetailActivity::class.java)
            intent.putExtra("book", selectedBook)
            startActivity(intent)
        }

        filterImageView.setOnClickListener {
            showFavoritesOnly = !showFavoritesOnly
            updateBookList()
        }
    }

    private fun loadMoreBooks() {
        val query = "ios"
        val maxResult = 20

        BookApi().searchBooks(
            query, maxResult, startIndex,
            onResponse = { booksResponse ->
                val newBooks = booksResponse.map {
                    it.copy(isFavorite = favoriteBookManager.isBookFavorite(it.title))
                }
                allBooks.addAll(newBooks)
                adapterBooks.addAll(newBooks)
                adapter.updateData(newBooks.toMutableList())
                adapter.notifyDataSetChanged()
            },
            onError = { error -> error.printStackTrace() })

        startIndex += maxResult
    }

    private fun updateBookList() {
        if (showFavoritesOnly) {
            val filteredFavorites = allBooks.filter { it.isFavorite }.toMutableList()
            adapter.updateData(filteredFavorites)
        } else {
            adapter.updateData(allBooks)
        }
    }
}

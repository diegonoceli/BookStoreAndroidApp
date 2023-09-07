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
    private var startIndex = 0
    private var showFavoritesOnly = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bookApi = BookApi()

        favoriteBookManager = FavoriteBookManager(this)
        itemListview = findViewById(R.id.itemListView)
        filterImageView = findViewById(R.id.filterIcon)
        adapter = BooksAdapter(allBooks, this)
        itemListview.adapter = adapter

        loadBooks()

        itemListview.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}

            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    startIndex = totalItemCount
                    updateBookList()
                    adapter.notifyDataSetChanged()
                }
            }
        }
        )

        itemListview.setOnItemClickListener { _, _, position, _ ->
            val selectedBook = allBooks[position] // Get the selected book from your data source

            val intent = Intent(this, BookDetailActivity::class.java)
            intent.putExtra("book", selectedBook)
            startActivity(intent)
        }

        filterImageView.setOnClickListener {
            showFavoritesOnly = !showFavoritesOnly
            updateBookList()
        }
    }

    private fun loadBooks() {
        val query = "ios"
        val maxResult = 20

        BookApi().searchBooks(query, maxResult, startIndex,
            onResponse = { booksResponse ->
                allBooks.addAll(booksResponse.map {
                    it.copy(isFavorite = favoriteBookManager.isBookFavorite(it.title))
                })
                adapter.notifyDataSetChanged()
            },
            onError = { error -> error.printStackTrace() })

        startIndex += maxResult
    }

    private fun updateBookList() {
        if (showFavoritesOnly) {
            adapter.updateData(allBooks.filter { it.isFavorite }.toMutableList())
        } else {
            loadBooks()
        }
    }
}
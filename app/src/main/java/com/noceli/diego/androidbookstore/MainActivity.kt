package com.noceli.diego.androidbookstore

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.noceli.diego.androidbookapi.Book
import com.noceli.diego.androidbookapi.BookApi
import com.noceli.diego.androidbookstore.adapters.BooksAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var bookApi: BookApi
    private lateinit var itemListview: ListView
    private lateinit var adapter: BooksAdapter

    private val books = mutableListOf<Book>()
    private var startIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bookApi = BookApi()

        itemListview = findViewById(R.id.itemListView)
        adapter = BooksAdapter(books, this)
        itemListview.adapter = adapter

        loadBooks()

        itemListview.setOnItemClickListener { _, _, position, _ ->
            val selectedBook = books[position] // Get the selected book from your data source

            val intent = Intent(this, BookDetailActivity::class.java)
            intent.putExtra("title", selectedBook.title)
            intent.putExtra("author", selectedBook.authors.joinToString(", "))
            intent.putExtra("description", selectedBook.description)
            intent.putExtra("buyLink", selectedBook.buyLink)
            intent.putExtra("imageThumbnail", selectedBook.imageThumbnail)
            startActivity(intent)
        }
    }

    private fun loadBooks() {
        val query = "ios"
        val maxResult = 20

        BookApi().searchBooks(query, maxResult, startIndex,
            onResponse = { booksResponse ->
                books.addAll(booksResponse)
                adapter.notifyDataSetChanged()
            },
            onError = { error -> error.printStackTrace() })

        startIndex += maxResult
    }
}
package com.noceli.diego.androidbookstore

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.noceli.diego.androidbookapi.Book
import com.noceli.diego.androidbookapi.openBuyLink
import com.squareup.picasso.Picasso

class BookDetailActivity : AppCompatActivity() {

    private lateinit var book: Book
    private lateinit var favoriteIcon: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var authorTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var buyButton: Button
    private lateinit var thumbnailImageView: ImageView
    private lateinit var favoriteBookManager: FavoriteBookManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        book = intent.getSerializableExtra("book") as Book

        val toolbar = findViewById<Toolbar>(R.id.toolbarDetails)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)

        favoriteBookManager = FavoriteBookManager(this)
        favoriteIcon = findViewById(R.id.favoriteIcon)
        titleTextView = findViewById(R.id.bookTitleDetail)
        authorTextView = findViewById(R.id.bookAuthorDetail)
        descriptionTextView = findViewById(R.id.bookDescriptionDetail)
        buyButton = findViewById(R.id.buyButton)
        thumbnailImageView = findViewById(R.id.imageThumbnail)

        getFavoriteState()

        favoriteIcon.setOnClickListener {
            book.isFavorite = !book.isFavorite
            updateFavoriteIconState(book.isFavorite)
            saveFavoriteState(book.isFavorite)
            Log.println(Log.DEBUG, null, "updated favorite to ${book.isFavorite}")
        }

        fillDetailsPage()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateFavoriteIconState(favorite: Boolean) {
        if (favorite) {
            favoriteIcon.setImageResource(R.drawable.ic_favorite_icon_colored)
        } else {
            favoriteIcon.setImageResource(R.drawable.ic_favorite_icon_border)
        }
    }

    private fun saveFavoriteState(isFavorite: Boolean) {
        favoriteBookManager.setBookFavorite(book.title, isFavorite)
    }

    private fun getFavoriteState() {
        val isFavorite = favoriteBookManager.isBookFavorite(book.title)
        updateFavoriteIconState(isFavorite)
    }

    private fun fillDetailsPage() {
        Picasso.get().load(book.imageThumbnail).into(thumbnailImageView)
        titleTextView.text = title
        authorTextView.text = "Author: ${book.authors.joinToString(", ")}"
        descriptionTextView.text = book.description

        val buyLink = book.buyLink
        if (buyLink.isNullOrEmpty()) {
            buyButton.visibility = View.GONE
        } else {
            buyButton.visibility = View.VISIBLE
            buyButton.setOnClickListener { openBuyLink(buyLink, this) }
        }
    }


}
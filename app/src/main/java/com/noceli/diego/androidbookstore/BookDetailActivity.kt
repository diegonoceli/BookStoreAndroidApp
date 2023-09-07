package com.noceli.diego.androidbookstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.noceli.diego.androidbookapi.openBuyLink
import com.squareup.picasso.Picasso

class BookDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbarDetails)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        val titleTextView = findViewById<TextView>(R.id.bookTitleDetail)
        val authorTextView = findViewById<TextView>(R.id.bookAuthorDetail)
        val descriptionTextView = findViewById<TextView>(R.id.bookDescriptionDetail)
        val buyButton = findViewById<Button>(R.id.buyButton)
        val thumbnailImageView = findViewById<ImageView>(R.id.imageThumbnail)

        fillDetailsPage(
            thumbnailImageView,
            titleTextView,
            authorTextView,
            descriptionTextView,
            buyButton
        )
    }

    private fun fillDetailsPage(
        thumbnailImageView: ImageView?,
        titleTextView: TextView,
        authorTextView: TextView,
        descriptionTextView: TextView,
        buyButton: Button
    ) {
        val title = intent.getStringExtra("title")
        val authors = intent.getStringExtra("author")
        val description = intent.getStringExtra("description")
        val buyLink = intent.getStringExtra("buyLink")
        val imageThumbnail = intent.getStringExtra("imageThumbnail")


        Picasso.get().load(imageThumbnail).into(thumbnailImageView)
        titleTextView.text = title
        authorTextView.text = "Author: $authors"
        descriptionTextView.text = description

        if (buyLink.isNullOrEmpty()) {
            buyButton.visibility = View.GONE
        } else {
            buyButton.visibility = View.VISIBLE
            buyButton.setOnClickListener {
                openBuyLink(buyLink, this)
            }
        }
    }


}
package com.noceli.diego.androidbookstore.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.noceli.diego.androidbookapi.Book
import com.noceli.diego.androidbookstore.R
import com.squareup.picasso.Picasso

class BooksAdapter(private val books: MutableList<Book>, private val context: Context) :
    BaseAdapter() {

    override fun getCount(): Int {
        return books.size
    }

    override fun getItem(position: Int): Any {
        return books[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val book = books[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_book, null)

        val thumbnailImageView: ImageView = view.findViewById(R.id.thumbnailImageView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val authorsTextView: TextView = view.findViewById(R.id.authorsTextView)

        authorsTextView.text = book.authors.joinToString(", ")
        titleTextView.text = book.title
        Picasso.get().load(book.imageThumbnail).into(thumbnailImageView)

        return view
    }

    fun updateData(newData: List<Book>) {
        books.clear()
        books.addAll(newData)
        notifyDataSetChanged()
    }
}


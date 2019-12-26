package com.cory.librarymanager.controller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.cory.librarymanager.R
import com.cory.librarymanager.controller.activity.SearchActivity
import com.cory.librarymanager.util.DBDao
import com.cory.librarymanager.model.Book
import com.cory.librarymanager.model.LoanRecord
import com.google.android.material.button.MaterialButton
import java.sql.Date
import java.util.*

class BookOverviewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val nameText: TextView = itemView.findViewById(R.id.name)
    private val authorText: TextView = itemView.findViewById(R.id.author)
    private val isbnText: TextView = itemView.findViewById(R.id.isbn)
    private val quantityText: TextView = itemView.findViewById(R.id.info)
    private val libIdText: TextView = itemView.findViewById(R.id.libId)
    private lateinit var book:Book

    fun bind(book: Book) {
        //更新图书数据
        this.book=book
        //更新UI数据
        nameText.text = book.name
        authorText.text = book.author
        isbnText.text = "ISBN ${book.id}"
        quantityText.text = "库存${book.quantity}/${book.totalQuantity}本"
        libIdText.text="${book.libraryId}书库"
    }
}

class BookOverviewAdapter(var list: List<Book>, private val context: Context) :
    RecyclerView.Adapter<BookOverviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookOverviewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.list_item_book_detail, parent, false)
        return BookOverviewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BookOverviewHolder, position: Int) {
        holder.bind(list[position])
    }
}
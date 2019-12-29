package com.cory.librarymanager.controller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cory.librarymanager.R
import com.cory.librarymanager.model.Book
import com.cory.librarymanager.model.LoanRecord

class ReaderLoanHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val nameText: TextView = itemView.findViewById(R.id.name)
    private val authorText: TextView = itemView.findViewById(R.id.author)
    private val isbnText: TextView = itemView.findViewById(R.id.isbn)
    private val dateText: TextView = itemView.findViewById(R.id.info)
    private lateinit var book:Book
    private lateinit var loanRecord: LoanRecord

    fun bind(book: Book,loanRecord: LoanRecord) {
        //更新数据
        this.book=book
        this.loanRecord=loanRecord
        //更新UI数据
        nameText.text = book.name
        authorText.text = book.author
        isbnText.text = "ISBN ${book.id}"
        dateText.text = "借阅于${loanRecord.loanDate}"
    }
}

class ReaderLoanAdapter(var bookList: List<Book>, var recordList: List<LoanRecord>, private val context: Context) :
    RecyclerView.Adapter<ReaderLoanHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReaderLoanHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.list_item_book_detail, parent, false)
        view.findViewById<TextView>(R.id.libId).visibility=View.INVISIBLE
        return ReaderLoanHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: ReaderLoanHolder, position: Int) {
        holder.bind(bookList[position],recordList[position])
    }
}
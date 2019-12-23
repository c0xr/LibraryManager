package com.cory.librarymanager.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.cory.librarymanager.R
import com.cory.librarymanager.dao.DBDao
import com.cory.librarymanager.model.Book
import com.cory.librarymanager.model.LoanRecord
import com.google.android.material.button.MaterialButton

class BookReturnHolder(itemView: View, private val context: Context) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val nameText: TextView = itemView.findViewById(R.id.name)
    private val authorText: TextView = itemView.findViewById(R.id.author)
    private val isbnText: TextView = itemView.findViewById(R.id.isbn)
    private lateinit var book:Book
    private lateinit var loanRecord: LoanRecord
    private val returnButton:MaterialButton=itemView.findViewById(R.id.bookReturn)

    init {
        returnButton.setOnClickListener(this)
    }

    fun bind(book: Book,loanRecord: LoanRecord) {
        //更新数据
        this.book=book
        this.loanRecord=loanRecord
        //更新UI数据
        nameText.text = book.name
        authorText.text = book.author
        isbnText.text = "ISBN ${book.id}"
    }

    override fun onClick(v: View?) {
        AlertDialog.Builder(context).setMessage("确认退还")
            .setPositiveButton("退还") { dialog, which ->
                val dao=DBDao.get(context)
                //删除借阅记录
                dao.deleteLoanRecord(loanRecord)
                book.quantity++
                //更新剩余图书数量
                dao.updateBookQuantity(book)
                //显示借阅成功UI提示
                (context as BookReturnActivity).showSnackBar()
            }
            .setNegativeButton("取消",null)
            .setCancelable(false)
            .show()
    }
}

class BookReturnAdapter(var bookList: List<Book>, var recordList: List<LoanRecord>, private val context: Context) :
    RecyclerView.Adapter<BookReturnHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookReturnHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.list_item_book_return, parent, false)
        return BookReturnHolder(view, context)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookReturnHolder, position: Int) {
        holder.bind(bookList[position],recordList[position])
    }
}
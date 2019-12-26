package com.cory.librarymanager.controller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.cory.librarymanager.R
import com.cory.librarymanager.controller.activity.BookReturnActivity
import com.cory.librarymanager.util.DBDao
import com.cory.librarymanager.model.Book
import com.cory.librarymanager.model.LoanRecord
import com.cory.librarymanager.util.log
import com.google.android.material.button.MaterialButton

class BookReturnHolder(itemView: View, private val context: Context,
                       private val adapter: BookReturnAdapter) :
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
        var msg="确认退还"
        if(System.currentTimeMillis()-loanRecord.loanDate.time>2592000000){
            msg="归还已逾期，缴纳罚金后退还"
        }
        AlertDialog.Builder(context).setMessage(msg)
            .setPositiveButton("退还") { dialog, which ->
                val dao= DBDao.get(context)
                //删除借阅记录
                dao.deleteLoanRecord(loanRecord)
                book.quantity++
                //更新剩余图书数量
                dao.updateBookQuantity(book)
                //显示借阅成功UI提示
                (context as BookReturnActivity).showSnackBar()
                adapter.bookList.removeAt(adapterPosition)
                adapter.recordList.removeAt(adapterPosition)
                log(adapter.recordList.size)
                if(adapter.recordList.size==0){
                    context.showInfo()
                }
                adapter.notifyItemRemoved(adapterPosition)
            }
            .setNegativeButton("取消",null)
            .show()
    }
}

class BookReturnAdapter(var bookList: MutableList<Book>, var recordList: MutableList<LoanRecord>, private val context: Context) :
    RecyclerView.Adapter<BookReturnHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookReturnHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.list_item_book_return, parent, false)
        return BookReturnHolder(view, context,this)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookReturnHolder, position: Int) {
        holder.bind(bookList[position],recordList[position])
    }
}
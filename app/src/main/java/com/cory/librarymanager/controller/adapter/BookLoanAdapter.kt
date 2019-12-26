package com.cory.librarymanager.controller.adapter

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.cory.librarymanager.controller.activity.SearchActivity
import com.cory.librarymanager.util.DBDao
import com.cory.librarymanager.model.Book
import com.cory.librarymanager.model.LoanRecord
import com.google.android.material.button.MaterialButton
import java.sql.Date
import java.util.*
import android.widget.LinearLayout
import com.cory.librarymanager.R
import com.cory.librarymanager.util.toast
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import androidx.core.content.ContextCompat.getSystemService
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


class BookLoanHolder(itemView: View, private val context: Context) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val nameText: TextView = itemView.findViewById(R.id.name)
    private val authorText: TextView = itemView.findViewById(R.id.author)
    private val isbnText: TextView = itemView.findViewById(R.id.isbn)
    private val quantityText: TextView = itemView.findViewById(R.id.quantity)
    private lateinit var book:Book
    private val loanButton:MaterialButton=itemView.findViewById(R.id.loan)

    init {
        loanButton.setOnClickListener(this)
    }

    fun bind(book: Book) {
        //更新图书数据
        this.book=book
        //更新UI数据
        nameText.text = book.name
        authorText.text = book.author
        isbnText.text = "ISBN ${book.id}"
        quantityText.text = "剩余${book.quantity}本"
        //检查图书剩余数量，禁用UI按钮
        if(book.quantity==0) loanButton.isEnabled=false
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        val input = EditText(context)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        input.inputType=InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(context).setMessage("填写读者学号")
            .setPositiveButton("确认借阅") { dialog, which ->
                val dao= DBDao.get(context)
                val account=input.text.toString()
                val reader=dao.getReader(account)
                if(reader==null){
                    toast("学号不存在",context)
                }else{
                    val card=dao.getLibraryCard(account)
                    if(card==null){
                        toast("该用户未办理借阅卡",context)
                    }else{
                        //增加借阅记录
                        val record=LoanRecord(
                            id = UUID.randomUUID().toString(),
                            readerId = account,
                            bookId = book.id,
                            loanDate = Date(System.currentTimeMillis())
                        )
                        dao.addLoanRecord(record)
                        book.quantity--
                        //更新UI显示的剩余数量
                        quantityText.text="剩余${book.quantity}本"
                        //检查图书剩余数量，禁用UI按钮
                        if(book.quantity==0) loanButton.isEnabled=false
                        //更新剩余图书数量
                        dao.updateBookQuantity(book)
                        //显示借阅成功UI提示
                        (context as SearchActivity).showSnackBar()
                    }
                }
            }
            .setNegativeButton("取消",null)
            .setView(input)
            .show()
        input.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            input.post {
                val inputMethodManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(input, SHOW_IMPLICIT)
            }
        }
        input.requestFocus()
    }
}

class BookLoanAdapter(var list: List<Book>, private val context: Context) :
    RecyclerView.Adapter<BookLoanHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookLoanHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.list_item_book_loan, parent, false)
        return BookLoanHolder(view, context)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BookLoanHolder, position: Int) {
        holder.bind(list[position])
    }
}
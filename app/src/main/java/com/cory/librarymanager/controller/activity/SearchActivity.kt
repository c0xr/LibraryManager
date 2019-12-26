package com.cory.librarymanager.controller.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cory.librarymanager.R
import com.cory.librarymanager.controller.adapter.BookLoanAdapter
import com.cory.librarymanager.util.DBDao
import com.cory.librarymanager.model.Book
import com.google.android.material.snackbar.Snackbar
import java.util.*

class SearchActivity : AppCompatActivity() {
    private lateinit var bookList:List<Book>
    private lateinit var snackBar:Snackbar

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        title="图书检索"
        val searchView = findViewById<SearchView>(R.id.search)
        val resultList = findViewById<RecyclerView>(R.id.result)
        //获得所有图书
        bookList = DBDao.get(this).getAllBooks()
        //设置适配器
        val adapter = BookLoanAdapter(bookList, this)
        resultList.adapter = adapter
        //设置布局管理器
        resultList.layoutManager = LinearLayoutManager(this)
        //设置分割线
        resultList.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                //过滤list数据
                adapter.list = bookList.filter {
                    val input=newText?.toLowerCase(Locale.CHINA) ?: ""
                    it.name.toLowerCase(Locale.CHINA).contains(input)||
                            it.author.toLowerCase(Locale.CHINA).contains(input)||
                            it.id.toLowerCase(Locale.CHINA).contains(input)
                }
                //通知更新UI
                adapter.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

        //初始化snackBar
        snackBar= Snackbar.make(
            findViewById(android.R.id.content),
            "借阅成功",
            Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(Color.WHITE)
        val text=snackBar.view
            .findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        text.setTextColor(getColor(R.color.orange))
    }

    fun showSnackBar(){
        snackBar.show()
    }
}

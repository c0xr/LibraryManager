package com.cory.librarymanager.controller

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
import com.cory.librarymanager.dao.DBDao
import com.google.android.material.snackbar.Snackbar

class BookReturnActivity : AppCompatActivity() {
    private lateinit var snackBar:Snackbar

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context,BookReturnActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_return)
        val searchView = findViewById<SearchView>(R.id.search)
        val resultList = findViewById<RecyclerView>(R.id.result)
        //设置适配器
        val adapter = BookReturnAdapter(emptyList(), emptyList(),this)
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
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val dao=DBDao.get(this@BookReturnActivity)
                val recordList=dao.getAllLoanRecords(query?:"")
                val bookList=dao.getAllBooks(recordList)
                adapter.bookList=bookList
                adapter.recordList=recordList
                //通知更新UI
                adapter.notifyDataSetChanged()
                return true
            }
        })

        //初始化snackBar
        snackBar= Snackbar.make(
            findViewById(android.R.id.content),
            "退还成功",
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

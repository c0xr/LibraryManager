package com.cory.librarymanager.controller.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cory.librarymanager.R
import com.cory.librarymanager.controller.adapter.BookReturnAdapter
import com.cory.librarymanager.model.Book
import com.cory.librarymanager.model.LoanRecord
import com.cory.librarymanager.util.DBDao
import com.google.android.material.snackbar.Snackbar

class BookReturnActivity : AppCompatActivity() {
    private lateinit var snackBar:Snackbar
    private lateinit var infoText:TextView

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, BookReturnActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_return)
        title="图书退还"
        val searchView = findViewById<SearchView>(R.id.search)
        val resultList = findViewById<RecyclerView>(R.id.result)
        infoText=findViewById<TextView>(R.id.info)
        //设置适配器
        val adapter = BookReturnAdapter(
            mutableListOf(),
            mutableListOf(),
            this
        )
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
                val dao= DBDao.get(this@BookReturnActivity)
                val recordList= mutableListOf<LoanRecord>()
                val bookList=mutableListOf<Book>()
                dao.getBooksAndRecords(query?:"",bookList,recordList)
                adapter.bookList=bookList
                adapter.recordList=recordList
                //通知更新UI
                adapter.notifyDataSetChanged()
                if(recordList.size==0){
                    infoText.visibility= View.VISIBLE
                }else{
                    infoText.visibility= View.INVISIBLE
                }
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

    fun showInfo(){
        infoText.visibility= View.VISIBLE
    }
}

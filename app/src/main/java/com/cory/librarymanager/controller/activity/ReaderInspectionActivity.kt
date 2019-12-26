package com.cory.librarymanager.controller.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cory.librarymanager.R
import com.cory.librarymanager.controller.adapter.ReaderLoanAdapter
import com.cory.librarymanager.model.Book
import com.cory.librarymanager.model.LoanRecord
import com.cory.librarymanager.util.DBDao
import com.cory.librarymanager.util.Table

class ReaderInspectionActivity : AppCompatActivity() {

    companion object{
        fun newIntent(context: Context,account:String):Intent{
            val intent=Intent(context,
                ReaderInspectionActivity::class.java)
            intent.putExtra(Table.Reader.ID,account)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reader_inspection_activity)
        title="读者信息"
        val dao= DBDao.get(this)
        val account=intent.getStringExtra(Table.Reader.ID)
        val reader = dao.getReader(account!!)!!
        val idText=findViewById<TextView>(R.id.id)
        val nameText=findViewById<TextView>(R.id.name)
        val telText=findViewById<TextView>(R.id.tel)
        val levelText=findViewById<TextView>(R.id.level)
        val card=dao.getLibraryCard(reader.id)
        idText.text=reader.id
        nameText.text= reader.name
        telText.text=reader.tel
        if(card!=null){
            levelText.text=card.level
        }else{
            levelText.text="未办卡"
        }

        val detailView=findViewById<RecyclerView>(R.id.detail)
        val recordList= mutableListOf<LoanRecord>()
        val bookList=mutableListOf<Book>()
        dao.getBooksAndRecords(account,bookList,recordList)
        detailView.adapter=
            ReaderLoanAdapter(bookList, recordList, this)
        detailView.layoutManager=LinearLayoutManager(this)
    }
}

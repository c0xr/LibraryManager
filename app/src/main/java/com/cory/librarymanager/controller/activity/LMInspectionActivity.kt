package com.cory.librarymanager.controller.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cory.librarymanager.R
import com.cory.librarymanager.controller.adapter.BookOverviewAdapter
import com.cory.librarymanager.util.DBDao
import com.cory.librarymanager.util.log

class LMInspectionActivity : AppCompatActivity() {
    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context,
                LMInspectionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lm_inspection)
        title="书库管理"
        val overviewView=findViewById<RecyclerView>(R.id.overview)
        val contentText=findViewById<TextView>(R.id.content)
        val dao=DBDao.get(this)
        val list=dao.getAllBooks()
        val quantityMap= mutableMapOf<String,Int>()
        val totalQuantityMap= mutableMapOf<String,Int>()
        list.forEach {
            if(quantityMap[it.libraryId]!=null){
                quantityMap[it.libraryId]=quantityMap[it.libraryId]!!+it.quantity
            }else{
                quantityMap[it.libraryId]=it.quantity
            }

            if(totalQuantityMap[it.libraryId]!=null){
                totalQuantityMap[it.libraryId]=totalQuantityMap[it.libraryId]!!+it.totalQuantity
            }else{
                totalQuantityMap[it.libraryId]=it.totalQuantity
            }
        }
        var content=""
        var isFirst=true
        quantityMap.forEach {
            if(isFirst){
                isFirst=false
            }else{
                content+="\n\n"
            }
            val loan=totalQuantityMap[it.key]!!-it.value
            content+="${it.key}书库 : 已借出${loan}本 , 库存${it.value}本"
        }
        contentText.text=content
        overviewView.adapter=BookOverviewAdapter(list,this)
        overviewView.layoutManager=LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.lm_inspection_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(
            PurchaseActivity.newIntent(
                this
            )
        )
        return true
    }
}

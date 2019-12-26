package com.cory.librarymanager.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import com.cory.librarymanager.R
import com.cory.librarymanager.dao.DBDao
import com.cory.librarymanager.model.LibraryCard
import com.cory.librarymanager.model.Reader
import java.sql.Date

class CardRegisterActivity : AppCompatActivity() {
    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context,CardRegisterActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_register)
        val dao=DBDao.get(this)
        var reader: Reader?=null
        val searchView=findViewById<SearchView>(R.id.search)
        val idText=findViewById<TextView>(R.id.id)
        val nameText=findViewById<TextView>(R.id.name)
        val telText=findViewById<TextView>(R.id.tel)
        val registerButton=findViewById<Button>(R.id.register)
        val infoLayout=findViewById<LinearLayout>(R.id.infoLayout)
        val info=findViewById<TextView>(R.id.info)
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                reader = dao.getReader(query?:"")
                if(reader!=null){
                    idText.text=reader!!.id
                    nameText.text= reader!!.name
                    telText.text=reader!!.tel
                    registerButton.isEnabled=true
                    infoLayout.visibility=View.VISIBLE
                    info.visibility=View.INVISIBLE
                }else{
                    registerButton.isEnabled=false
                    infoLayout.visibility=View.INVISIBLE
                    info.visibility=View.VISIBLE
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        registerButton.setOnClickListener {
            val card=LibraryCard(
                reader!!.id,
                "会员",
                Date(System.currentTimeMillis())
            )
            dao.addLibraryCard(card)
            finish()
        }
    }
}

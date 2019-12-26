package com.cory.librarymanager.controller.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import com.cory.librarymanager.R
import com.cory.librarymanager.util.DBDao
import com.cory.librarymanager.model.LibraryCard
import com.cory.librarymanager.model.Reader
import com.cory.librarymanager.util.toast
import java.sql.Date

class CardRegisterActivity : AppCompatActivity() {
    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context,
                CardRegisterActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_register)
        title="借阅卡办理"
        val dao= DBDao.get(this)
        var reader: Reader?=null
        val searchView=findViewById<SearchView>(R.id.search)
        val idText=findViewById<TextView>(R.id.id)
        val nameText=findViewById<TextView>(R.id.name)
        val telText=findViewById<TextView>(R.id.tel)
        val registerButton=findViewById<Button>(R.id.register)
        val infoLayout=findViewById<LinearLayout>(R.id.infoLayout)
        val infoText=findViewById<TextView>(R.id.info)
        val levelText=findViewById<TextView>(R.id.level)
        registerButton.isEnabled=false
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                reader = dao.getReader(query?:"")
                if(reader!=null){
                    val card=dao.getLibraryCard(reader!!.id)
                    idText.text=reader!!.id
                    nameText.text= reader!!.name
                    telText.text=reader!!.tel
                    registerButton.isEnabled=true
                    infoLayout.visibility=View.VISIBLE
                    infoText.visibility=View.INVISIBLE
                    if(card!=null){
                        levelText.text=card.level
                        registerButton.isEnabled=false
                    }else{
                        levelText.text="未办卡"
                    }
                }else{
                    registerButton.isEnabled=false
                    infoLayout.visibility=View.INVISIBLE
                    infoText.visibility=View.VISIBLE
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
            toast("借阅卡办理成功",this)
            finish()
        }
    }
}

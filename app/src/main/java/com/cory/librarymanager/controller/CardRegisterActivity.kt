package com.cory.librarymanager.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import com.cory.librarymanager.R
import com.cory.librarymanager.dao.DBDao

class CardRegisterActivity : AppCompatActivity() {
    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context,CardRegisterActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_register)

        val searchView=findViewById<SearchView>(R.id.search)
        val idText=findViewById<TextView>(R.id.id)
        val nameText=findViewById<TextView>(R.id.name)
        val telText=findViewById<TextView>(R.id.tel)
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val dao=DBDao.get(this@CardRegisterActivity)
                val reader = dao.getReader(query?:"")
                if(reader!=null){

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }
}

package com.cory.librarymanager.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import com.cory.librarymanager.R

class SearchActivity : AppCompatActivity() {

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context,SearchActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val serachView=findViewById<SearchView>(R.id.search)
        val result=findViewById<TextView>(R.id.result)

        serachView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                result.text=newText
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}

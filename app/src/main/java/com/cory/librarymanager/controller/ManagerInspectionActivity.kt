package com.cory.librarymanager.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cory.librarymanager.R

class ManagerInspectionActivity : AppCompatActivity() {
    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context,ManagerInspectionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_inspection)
        findViewById<Button>(R.id.search).setOnClickListener {
            startActivity(SearchActivity.newIntent(this))
        }
        findViewById<Button>(R.id.bookReturn).setOnClickListener {
            startActivity(BookReturnActivity.newIntent(this))
        }
    }
}

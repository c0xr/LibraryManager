package com.cory.librarymanager.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cory.librarymanager.R

class LMInspectionActivity : AppCompatActivity() {
    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context,LMInspectionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_manager_inspection)

        val purchaseButton=findViewById<Button>(R.id.purchase)
        purchaseButton.setOnClickListener {
            startActivity(PurchaseActivity.newIntent(this))
        }
    }
}

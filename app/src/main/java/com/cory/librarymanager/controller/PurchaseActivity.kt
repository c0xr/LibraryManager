package com.cory.librarymanager.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.cory.librarymanager.R
import com.cory.librarymanager.dao.DBDao
import com.cory.librarymanager.model.Book
import java.sql.Date
import java.text.NumberFormat

class PurchaseActivity : AppCompatActivity() {
    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context,PurchaseActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase)

        val commitButton=findViewById<Button>(R.id.commit)
        val isbnText=findViewById<EditText>(R.id.isbn)
        val nameText=findViewById<EditText>(R.id.name)
        val pressText=findViewById<EditText>(R.id.press)
        val authorText=findViewById<EditText>(R.id.author)
        val publishDateText=findViewById<EditText>(R.id.publishDate)
        val priceText=findViewById<EditText>(R.id.price)
        val quantityText=findViewById<EditText>(R.id.quantity)
        val libraryIdText=findViewById<EditText>(R.id.libraryId)
        commitButton.setOnClickListener {
            val book= Book(
                isbnText.text.toString(),
                nameText.text.toString(),
                pressText.text.toString(),
                authorText.text.toString(),
                Date.valueOf(publishDateText.text.toString()),
                priceText.text.toString().toFloat(),
                Date(System.currentTimeMillis()),
                quantityText.text.toString().toInt(),
                quantityText.text.toString().toInt(),
                libraryIdText.text.toString()
            )
            val dao=DBDao.get(this)
            dao.addBook(book)
            finish()
        }
    }
}

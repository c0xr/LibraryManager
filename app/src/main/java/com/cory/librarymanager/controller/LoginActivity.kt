package com.cory.librarymanager.controller

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cory.librarymanager.R
import com.cory.librarymanager.util.DBHelper
import com.cory.librarymanager.util.Table
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = DBHelper(this).writableDatabase

        val accountText=findViewById<TextInputEditText>(R.id.account)
        val passwordText=findViewById<TextInputEditText>(R.id.password)
        findViewById<MaterialButton>(R.id.login).setOnClickListener {
            val account=accountText.text.toString()
            val password=passwordText.text.toString()

            var cursor = loginForReader(db,account,password)
            if(cursor.moveToNext()){
                Toast.makeText(this@LoginActivity,"欢迎进入图书管理",Toast.LENGTH_SHORT)
                    .show()
                startActivity(ReaderInspectionActivity.newIntent(this@LoginActivity))
                cursor.close()
                Toast.makeText(this@LoginActivity,"账号或密码错误",Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            cursor = loginForManager(db,account,password)
            if(cursor.moveToNext()){
                Toast.makeText(this@LoginActivity,"欢迎进入图书管理",Toast.LENGTH_SHORT)
                    .show()
                startActivity(ManagerInspectionActivity.newIntent(this@LoginActivity))
                cursor.close()
                Toast.makeText(this@LoginActivity,"账号或密码错误",Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            cursor = loginForLibraryManager(db,account,password)
            if(cursor.moveToNext()){
                Toast.makeText(this@LoginActivity,"欢迎进入图书管理",Toast.LENGTH_SHORT)
                    .show()
                startActivity(LMInspectionActivity.newIntent(this@LoginActivity))
                cursor.close()
                Toast.makeText(this@LoginActivity,"账号或密码错误",Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }
    }

    private fun loginForReader(db:SQLiteDatabase,account:String,password:String)=
        db.query(
            Table.Reader.tableName,
            arrayOf(Table.Reader.Column.id),
            "${Table.Reader.Column.id}=? and ${Table.Reader.Column.password}=?",
            arrayOf(account, password), null, null, null
        )

    private fun loginForManager(db:SQLiteDatabase,account:String,password:String)=
        db.query(
            Table.Manager.tableName,
            arrayOf(Table.Manager.Column.id),
            "${Table.Manager.Column.id}=? and ${Table.Manager.Column.password}=?",
            arrayOf(account, password), null, null, null
        )

    private fun loginForLibraryManager(db:SQLiteDatabase,account:String,password:String)=
        db.query(
            Table.LibraryManager.tableName,
            arrayOf(Table.LibraryManager.Column.id),
            "${Table.LibraryManager.Column.id}=? and" +
                    " ${Table.LibraryManager.Column.password}=?",
            arrayOf(account, password), null, null, null
        )
}

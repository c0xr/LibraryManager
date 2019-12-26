package com.cory.librarymanager.controller.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cory.librarymanager.R
import com.cory.librarymanager.model.Book
import com.cory.librarymanager.util.DBDao
import com.cory.librarymanager.util.toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val dao = DBDao.get(this)
        val accountText = findViewById<TextInputEditText>(R.id.account)
        val passwordText = findViewById<TextInputEditText>(R.id.password)
        findViewById<MaterialButton>(R.id.login).setOnClickListener {
            val account = accountText.text.toString()
            val password = passwordText.text.toString()
            //尝试读者登录阶段
            if (dao.loginForReader(account, password)) {
                toast("欢迎进入图书管理",this)
                startActivity(
                    ReaderInspectionActivity.newIntent(
                        this,
                        account
                    )
                )
                return@setOnClickListener
            }
            //尝试管理员登录阶段
            if (dao.loginForManager(account, password)) {
                toast("欢迎进入图书管理",this)
                startActivity(
                    ManagerInspectionActivity.newIntent(
                        this
                    )
                )
                return@setOnClickListener
            }
            //尝试图书管理员登录阶段
            if (dao.loginForLibraryManager(account, password)) {
                toast("欢迎进入图书管理",this)
                startActivity(
                    LMInspectionActivity.newIntent(
                        this
                    )
                )
                return@setOnClickListener
            }
            //登录失败阶段
            toast("用户名或密码错误",this)
        }
        dao.getBooksAndRecords("20176505",mutableListOf(), mutableListOf())
    }
}

package com.cory.librarymanager.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cory.librarymanager.R
import com.cory.librarymanager.dao.DBDao
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val accountText = findViewById<TextInputEditText>(R.id.account)
        val passwordText = findViewById<TextInputEditText>(R.id.password)
        findViewById<MaterialButton>(R.id.login).setOnClickListener {
            val account = accountText.text.toString()
            val password = passwordText.text.toString()
            val dao = DBDao.get(this@LoginActivity)
            //尝试读者登录阶段
            if (dao.loginForReader(account, password)) {
                toast("欢迎进入图书管理")
                startActivity(ReaderInspectionActivity.newIntent(this@LoginActivity))
                return@setOnClickListener
            }
            //尝试管理员登录阶段
            if (dao.loginForManager(account, password)) {
                toast("欢迎进入图书管理")
                startActivity(ManagerInspectionActivity.newIntent(this@LoginActivity))
                return@setOnClickListener
            }
            //尝试图书管理员登录阶段
            if (dao.loginForLibraryManager(account, password)) {
                toast("欢迎进入图书管理")
                startActivity(LMInspectionActivity.newIntent(this@LoginActivity))
                return@setOnClickListener
            }
            //登录失败阶段
            toast("用户名或密码错误")
        }
    }

    private fun toast(info: String) {
        Toast.makeText(this@LoginActivity, info, Toast.LENGTH_SHORT)
            .show()
    }
}

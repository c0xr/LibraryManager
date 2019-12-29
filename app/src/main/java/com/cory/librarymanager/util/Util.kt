package com.cory.librarymanager.util

import android.content.Context
import android.util.Log
import android.widget.Toast

fun log(any:Any?){
    Log.d("TAGG",any?.toString()?:"null")
}

fun toast(info: String,context: Context) {
    Toast.makeText(context, info, Toast.LENGTH_SHORT)
        .show()
}
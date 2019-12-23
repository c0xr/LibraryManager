package com.cory.librarymanager.model

import java.sql.Date

data class Book(
    val id: String,
    val name: String,
    val press: String,
    val author: String,
    val publishDate: Date,
    val price: Float,
    val registerDate: Date,
    var quantity: Int,
    val totalQuantity: Int,
    val libraryId: String
)
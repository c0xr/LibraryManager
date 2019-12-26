package com.cory.librarymanager.model

import java.sql.Date

data class LibraryCard (
    val readerId:String,
    val level:String,
    val registerDate:Date
)
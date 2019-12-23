package com.cory.librarymanager.model

import java.sql.Date

data class LoanRecord(
    val id: String,
    val readerId: String,
    val bookId: String,
    val loanDate: Date
)
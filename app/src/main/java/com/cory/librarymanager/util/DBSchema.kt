package com.cory.librarymanager.util

class Table {
    class Reader {
        companion object {
            const val TABLE_NAME = "reader"
            const val ID = "id"
            const val PASSWORD = "password"
            const val NAME = "name"
            const val TEL = "tel"
        }
    }

    class Manager {
        companion object {
            const val TABLE_NAME = "manager"
            const val ID = "ID"
            const val PASSWORD = "password"
            const val NAME = "name"
            const val TEL = "tel"
        }
    }

    class LibraryManager {
        companion object {
            const val TABLE_NAME = "libraryManager"
            const val ID = "ID"
            const val PASSWORD = "password"
            const val NAME = "name"
            const val TEL = "tel"
            const val LIBRARYID = "libraryId"
        }
    }

    class Book {
        companion object {
            const val TABLE_NAME = "book"
            const val ID = "ID"
            const val NAME = "name"
            const val PRESS = "press"
            const val AUTHOR = "author"
            const val PUBLISH_DATE = "publishDate"
            const val PRICE = "price"
            const val REGISTER_DATE = "registerDate"
            const val QUANTITY = "quantity"
            const val TOTAL_QUANTITY = "totalQuantity"
            const val LIBRARY_ID = "libraryId"
        }
    }

    class LoanRecord {
        companion object {
            const val TABLE_NAME = "loanRecord"
            const val ID = "id"
            const val READER_ID = "readerId"
            const val BOOK_ID = "bookId"
            const val LOAN_DATE = "loanDate"
        }
    }
}
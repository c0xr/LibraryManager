package com.cory.librarymanager.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.cory.librarymanager.model.Book
import java.sql.Date
import android.content.ContentValues
import com.cory.librarymanager.model.LibraryCard
import com.cory.librarymanager.model.LoanRecord
import com.cory.librarymanager.model.Reader


class DBDao private constructor(context: Context) {
    companion object {
        private var instance: DBDao? = null
        fun get(context: Context): DBDao {
            if (instance == null) {
                instance =
                    DBDao(context)
            }
            return instance!!
        }
    }
    private val db:SQLiteDatabase = DBHelper(context).writableDatabase

    fun loginForReader(account:String,password:String):Boolean{
        with(Table.Reader){
            val cursor=db.query(
                TABLE_NAME,
                arrayOf(ID),
                "$ID=? and ${PASSWORD}=?",
                arrayOf(account, password), null, null, null
            )
            val isLogin=cursor.moveToNext()
            cursor.close()
            return isLogin
        }
    }

    fun loginForManager(account:String,password:String):Boolean{
        with(Table.Manager){
            val cursor=db.query(
                TABLE_NAME,
                arrayOf(ID),
                "$ID=? and ${PASSWORD}=?",
                arrayOf(account, password), null, null, null
            )
            val isLogin=cursor.moveToNext()
            cursor.close()
            return isLogin
        }
    }


    fun loginForLibraryManager(account:String,password:String):Boolean{
        with(Table.LibraryManager){
            val cursor=db.query(
                TABLE_NAME,
                arrayOf(ID),
                "$ID=? and ${PASSWORD}=?",
                arrayOf(account, password), null, null, null
            )
            val isLogin=cursor.moveToNext()
            cursor.close()
            return isLogin
        }
    }

    fun addBook(book: Book){
        val values=ContentValues()
        with(Table.Book){
            values.put(ID,book.id)
            values.put(NAME,book.name)
            values.put(PRESS,book.press)
            values.put(AUTHOR,book.author)
            values.put(PUBLISH_DATE,book.publishDate.toString())
            values.put(PRICE,book.price)
            values.put(REGISTER_DATE,book.registerDate.toString())
            values.put(QUANTITY,book.quantity)
            values.put(TOTAL_QUANTITY,book.totalQuantity)
            values.put(LIBRARY_ID,book.libraryId)
            db.insert(TABLE_NAME,null,values)
        }
    }

    fun updateBookQuantity(book: Book){
        val values = ContentValues()
        with(Table.Book){
            values.put(QUANTITY, book.quantity)
            db.update(TABLE_NAME, values, "$ID=?", arrayOf(book.id))
        }
    }

    fun getAllBooks():List<Book>{
        val list= mutableListOf<Book>()
        val cursor=db.rawQuery("select * from ${Table.Book.TABLE_NAME}",null)
        while (cursor.moveToNext()){
            val book=Book(
                id = cursor.getString(0),
                name = cursor.getString(1),
                press = cursor.getString(2),
                author = cursor.getString(3),
                publishDate = Date.valueOf(cursor.getString(4)),
                price = cursor.getFloat(5),
                registerDate = Date.valueOf(cursor.getString(6)),
                quantity = cursor.getInt(7),
                totalQuantity = cursor.getInt(8),
                libraryId = cursor.getString(9)
            )
            list.add(book)
        }
        cursor.close()
        return list
    }

    fun getBooksAndRecords(account: String,bookList: MutableList<Book>,
                           recordList: MutableList<LoanRecord>){
        val cursor=db.rawQuery("select * from ${Table.Book.TABLE_NAME}," +
                " ${Table.LoanRecord.TABLE_NAME}" +
                " where ${Table.LoanRecord.READER_ID}=?" +
                " and ${Table.LoanRecord.BOOK_ID}=${Table.Book.TABLE_NAME}.${Table.Book.ID}",
            arrayOf(account)
        )
        while (cursor.moveToNext()){
            bookList.add(
                Book(
                    id = cursor.getString(0),
                    name = cursor.getString(1),
                    press = cursor.getString(2),
                    author = cursor.getString(3),
                    publishDate = Date.valueOf(cursor.getString(4)),
                    price = cursor.getFloat(5),
                    registerDate = Date.valueOf(cursor.getString(6)),
                    quantity = cursor.getInt(7),
                    totalQuantity = cursor.getInt(8),
                    libraryId = cursor.getString(9)
               )
            )
            recordList.add(
                LoanRecord(
                    id = cursor.getString(10),
                    readerId = cursor.getString(11),
                    bookId = cursor.getString(12),
                    loanDate = Date.valueOf(cursor.getString(13))
                )
            )
        }
        cursor.close()
    }

    fun addLoanRecord(loanRecord: LoanRecord){
        val values=ContentValues()
        with(Table.LoanRecord){
            values.put(ID,loanRecord.id)
            values.put(READER_ID,loanRecord.readerId)
            values.put(BOOK_ID,loanRecord.bookId)
            values.put(LOAN_DATE,loanRecord.loanDate.toString())
            db.insert(TABLE_NAME,null,values)
        }
    }

    fun deleteLoanRecord(loanRecord: LoanRecord){
        with(Table.LoanRecord){
            db.delete(TABLE_NAME,"$ID=?", arrayOf(loanRecord.id))
        }
    }

    fun getReader(account: String):Reader?{
        with(Table.Reader){
            val cursor=db.rawQuery("select * from $TABLE_NAME where $ID=?",
                arrayOf(account)
            )
            var reader:Reader?=null
            if(cursor.moveToFirst()){
                reader=Reader(
                    id = cursor.getString(0),
                    password = cursor.getString(1),
                    name = cursor.getString(2),
                    tel = cursor.getString(3)
                )
            }
            cursor.close()
            return reader
        }
    }

    fun getLibraryCard(id:String):LibraryCard?{
        with(Table.LibraryCard){
            var card:LibraryCard?=null
            val cursor=db.rawQuery("select * from $TABLE_NAME where $READER_ID=?",
                arrayOf(id))
            if(cursor.moveToFirst()){
                card= LibraryCard(
                    cursor.getString(0),
                    cursor.getString(1),
                    Date.valueOf(cursor.getString(2))
                )
            }
            cursor.close()
            return card
        }
    }

    fun addLibraryCard(libraryCard: LibraryCard){
        with(Table.LibraryCard){
            val values = ContentValues()
            values.put(READER_ID,libraryCard.readerId)
            values.put(LEVEL,libraryCard.level)
            values.put(REGISTER_DATE,Date(System.currentTimeMillis()).toString())
            db.insert(TABLE_NAME,null,values)
        }
    }
}
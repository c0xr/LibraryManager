package com.cory.librarymanager.util

class Table{
    class Reader{
        companion object{
            const val tableName="reader"
        }
        class Column{
            companion object{
                const val id="id"
                const val password="password"
            }
        }
    }

    class Manager{
        companion object{
            const val tableName="manager"
        }
        class Column{
            companion object{
                const val id="id"
                const val password="password"
                const val name="name"
                const val tel="tel"
            }
        }
    }

    class LibraryManager{
        companion object{
            const val tableName="libraryManager"
        }
        class Column{
            companion object{
                const val id="id"
                const val password="password"
                const val name="name"
                const val tel="tel"
                const val libraryId="libraryId"
            }
        }
    }
}
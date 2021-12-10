package org.coderpwh.mydiary

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {
    private val createBook = "create table DiaryData (" +
            "user text," +
            "title text," +
            "content text," +
            "time text)"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createBook)
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {   }
}
package com.example.mynotes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.media.projection.MediaProjection
import android.widget.Toast

class DbManager {
    val dbName="MyNotes"
    val dbTable = "Notes"
    val colID ="ID"
    val colTitle ="Title"
    val colDes = "Description"
    val dbVersion =1
    val sqlCreateTable="CREATE TABLE IF NOT EXISTS "+ dbTable +" ("+ colID +" INTEGER PRIMARY KEY,"+
            colTitle + " TEXT, "+ colDes +" TEXT);"
    var sqlDB:SQLiteDatabase?=null
    constructor(context: Context){
        var db = DatabaseHelperNotes(context)
        sqlDB = db.writableDatabase

    }

    inner class DatabaseHelperNotes:SQLiteOpenHelper{
        var context:Context ?=null
        constructor(context:Context):super(context, dbName, null, dbVersion){
           this.context =context
        }
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context, "Database is created",Toast.LENGTH_LONG).show()

        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS "+dbTable)
        }

    }
    fun Insert(value:ContentValues):Long{
       val ID =sqlDB!!.insert(dbTable, "", value)
        return  ID
    }
    fun Query(projection: Array<String>,selection:String,selectionArg:Array<String>, SortOrder:String):Cursor{
        val qb = SQLiteQueryBuilder()
        qb.tables =dbTable
        val cursor =qb.query(sqlDB,projection,selection,selectionArg,null,null,SortOrder)
        return cursor
    }
    fun Delete(selection: String,selectionArg: Array<String>):Int{
        val count =sqlDB!!.delete(dbTable,selection,selectionArg)
        return  count
    }
    fun Update(value:ContentValues,selection: String,selectionArg: Array<String>):Int {
        val count =sqlDB!!.update(dbTable,value,selection,selectionArg)
        return count
    }
}
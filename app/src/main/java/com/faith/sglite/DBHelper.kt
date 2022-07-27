package com.faith.sglite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                PRICE_COL + "INTEGER," +
                QUANTITY_COL + " INTEGER" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addProduct(name: String, price: Int, quatity: Int) {

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(NAME_COl, name)
        values.put(PRICE_COL, price)
        values.put(QUANTITY_COL, quatity)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getProducts(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }


    fun deleteProduct(id: Int) {
        val db = this.writableDatabase
        val selection= arrayOf(id.toString())
        db.delete(TABLE_NAME,"$ID_COL =7",selection)
        db.close()
    }

    fun fetchOneProduct(id: Int): Cursor? {
        val sql = "DELETE FROM $TABLE_NAME WHERE $ID_COL - $id"
        val db = this.readableDatabase
        return db.rawQuery(sql, null)
    }

    fun updateProduct(id: Int, name: String, quantity: Int, price: Int) {
        val contentValues=ContentValues()
        contentValues.put(NAME_COl,name)
        contentValues.put(QUANTITY_COL,quantity)
        contentValues.put(PRICE_COL,price)
        val db = this .readableDatabase
        db.update(TABLE_NAME,contentValues,"$ID_COL =7", arrayOf(id.toString()))
    }

    fun searchProduct(search: String): Cursor {
        val sql = "SELECT *FROM $TABLE_NAME WHERE $NAME_COl LIKE '$search'"
        val db = this.readableDatabase
         return db.rawQuery(sql, null)

    }

    companion object {
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "shop_db"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "products"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val NAME_COl = "name"

        // below is the variable for price column
        val PRICE_COL = "price"

        // below is the variable for quanity column
        val QUANTITY_COL = "quantity"
    }
}
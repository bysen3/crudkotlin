package com.example.crudkotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.ArrayList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    val allUsers: ArrayList<UserModel>
        get() {
            val userModelArrayList = ArrayList<UserModel>()

            val selectQuery = "SELECT  * FROM $TABLE_USER"
            val db = this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if (c.moveToFirst()) {
                do {
                    val userModel = UserModel()
                    userModel.setIds(c.getInt(c.getColumnIndex(KEY_ID)))
                    userModel.setNames(c.getString(c.getColumnIndex(KEY_FIRSTNAME)))
                    val selectHobbyQuery =
                        "SELECT  * FROM " + TABLE_USER_HOBBY + " WHERE " + KEY_ID + " = " + userModel.getIds()
                    Log.d("oppp", selectHobbyQuery)
                    val cHobby = db.rawQuery(selectHobbyQuery, null)

                    if (cHobby.moveToFirst()) {
                        do {
                            userModel.setHobbys(cHobby.getString(cHobby.getColumnIndex(KEY_HOBBY)))
                        } while (cHobby.moveToNext())
                    }
                    val selectCityQuery =
                        "SELECT  * FROM " + TABLE_USER_CITY + " WHERE " + KEY_ID + " = " + userModel.getIds()
                    val cCity = db.rawQuery(selectCityQuery, null)

                    if (cCity.moveToFirst()) {
                        do {
                            userModel.setCitys(cCity.getString(cCity.getColumnIndex(KEY_CITY)))
                        } while (cCity.moveToNext())
                    }
                    userModelArrayList.add(userModel)
                } while (c.moveToNext())
            }
            return userModelArrayList
        }

    init {

        Log.d("table", CREATE_TABLE_STUDENTS)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_STUDENTS)
        db.execSQL(CREATE_TABLE_USER_HOBBY)
        db.execSQL(CREATE_TABLE_USER_CITY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_USER'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_USER_HOBBY'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_USER_CITY'")
        onCreate(db)
    }

    fun addUser(name: String, hobby: String, city: String) {
        val db = this.writableDatabase
        //adding user name in users table
        val values = ContentValues()
        values.put(KEY_FIRSTNAME, name)
        // db.insert(TABLE_USER, null, values);
        val id = db.insertWithOnConflict(TABLE_USER, null, values, SQLiteDatabase.CONFLICT_IGNORE)

        //adding user hobby in users_hobby table
        val valuesHobby = ContentValues()
        valuesHobby.put(KEY_ID, id)
        valuesHobby.put(KEY_HOBBY, hobby)
        db.insert(TABLE_USER_HOBBY, null, valuesHobby)

        //adding user city in users_city table
        val valuesCity = ContentValues()
        valuesCity.put(KEY_ID, id)
        valuesCity.put(KEY_CITY, city)
        db.insert(TABLE_USER_CITY, null, valuesCity)
    }

    fun updateUser(id: Int, name: String, hobby: String, city: String) {
        val db = this.writableDatabase

        // updating name in users table
        val values = ContentValues()
        values.put(KEY_FIRSTNAME, name)
        db.update(TABLE_USER, values, "$KEY_ID = ?", arrayOf(id.toString()))

        // updating hobby in users_hobby table
        val valuesHobby = ContentValues()
        valuesHobby.put(KEY_HOBBY, hobby)
        db.update(TABLE_USER_HOBBY, valuesHobby, "$KEY_ID = ?", arrayOf(id.toString()))

        // updating city in users_city table
        val valuesCity = ContentValues()
        valuesCity.put(KEY_CITY, city)
        db.update(TABLE_USER_CITY, valuesCity, "$KEY_ID = ?", arrayOf(id.toString()))
    }

    fun deleteUSer(id: Int) {

        // delete row in students table based on id
        val db = this.writableDatabase

        //deleting from users table
        db.delete(TABLE_USER, "$KEY_ID = ?", arrayOf(id.toString()))

        //deleting from users_hobby table
        db.delete(TABLE_USER_HOBBY, "$KEY_ID = ?", arrayOf(id.toString()))

        //deleting from users_city table
        db.delete(TABLE_USER_CITY, "$KEY_ID = ?", arrayOf(id.toString()))
    }

    companion object {

        var DATABASE_NAME = "user_database"
        private val DATABASE_VERSION = 2
        private val TABLE_USER = "users"
        private val TABLE_USER_HOBBY = "users_hobby"
        private val TABLE_USER_CITY = "users_city"
        private val KEY_ID = "id"
        private val KEY_FIRSTNAME = "name"
        private val KEY_HOBBY = "hobby"
        private val KEY_CITY = "city"

        /*CREATE TABLE students ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_number TEXT......);*/

        private val CREATE_TABLE_STUDENTS = ("CREATE TABLE "
                + TABLE_USER + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FIRSTNAME + " TEXT );")

        private val CREATE_TABLE_USER_HOBBY = ("CREATE TABLE "
                + TABLE_USER_HOBBY + "(" + KEY_ID + " INTEGER," + KEY_HOBBY + " TEXT );")

        private val CREATE_TABLE_USER_CITY = ("CREATE TABLE "
                + TABLE_USER_CITY + "(" + KEY_ID + " INTEGER," + KEY_CITY + " TEXT );")
    }

}
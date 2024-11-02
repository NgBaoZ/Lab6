package com.example.bai2

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "contacts.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_CONTACTS = "contacts"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_CONTACTS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_PHONE TEXT, $COLUMN_EMAIL TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addContact(contact: Contact): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, contact.name)
        values.put(COLUMN_PHONE, contact.phone)
        values.put(COLUMN_EMAIL, contact.email)

        val result = db.insert(TABLE_CONTACTS, null, values)
        db.close()
        return result
    }

    @SuppressLint("Range")
    fun getAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_CONTACTS", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
                val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
                contacts.add(Contact(id, name, phone, email))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return contacts
    }

    @SuppressLint("Range")
    fun getContactById(contactId: Long): Contact {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(TABLE_CONTACTS, null, "$COLUMN_ID = ?", arrayOf(contactId.toString()), null, null, null)

        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
            cursor.close()
            db.close()
            return Contact(id, name, phone, email)
        }
        cursor.close()
        db.close()
        throw Exception("Contact not found")
    }

    fun deleteContact(contactId: Long): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_CONTACTS, "$COLUMN_ID = ?", arrayOf(contactId.toString()))
        db.close()
        return result > 0 // Trả về true nếu xóa thành công
    }
}

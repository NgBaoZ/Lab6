package com.example.bai3.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bai3.model.Note
import java.util.*

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notes.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NOTES = "notes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_CREATED_AT = "created_at"
        private const val COLUMN_IS_IMPORTANT = "is_important"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NOTES ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT, $COLUMN_CREATED_AT INTEGER, "
                + "$COLUMN_IS_IMPORTANT INTEGER)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")
        onCreate(db)
    }

    fun addNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, note.title)
        values.put(COLUMN_CONTENT, note.content)
        values.put(COLUMN_CREATED_AT, note.createdAt.time)
        values.put(COLUMN_IS_IMPORTANT, if (note.isImportant) 1 else 0)

        db.insert(TABLE_NOTES, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllNotes(): List<Note> {
        val notes = mutableListOf<Note>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NOTES", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT))
                val createdAt = Date(cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED_AT)))
                val isImportant = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_IMPORTANT)) == 1

                notes.add(Note(id, title, content, createdAt, isImportant))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return notes
    }

    @SuppressLint("Range")
    fun getNoteById(id: Long): Note {
        val db = readableDatabase
        val cursor = db.query(TABLE_NOTES, null, "$COLUMN_ID=?", arrayOf(id.toString()), null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT))
            val createdAt = Date(cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED_AT)))
            val isImportant = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_IMPORTANT)) == 1

            cursor.close()
            db.close()
            return Note(id, title, content, createdAt, isImportant)
        }
        cursor.close()
        db.close()
        throw Exception("Note not found")
    }

    fun updateNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, note.title)
        values.put(COLUMN_CONTENT, note.content)
        values.put(COLUMN_CREATED_AT, note.createdAt.time)
        values.put(COLUMN_IS_IMPORTANT, if (note.isImportant) 1 else 0)

        db.update(TABLE_NOTES, values, "$COLUMN_ID=?", arrayOf(note.id.toString()))
        db.close()
    }

    fun deleteNote(id: Long) {
        val db = writableDatabase
        db.delete(TABLE_NOTES, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
    }
}

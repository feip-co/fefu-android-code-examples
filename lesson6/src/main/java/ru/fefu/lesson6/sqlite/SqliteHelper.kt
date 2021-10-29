package ru.fefu.lesson6.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.fefu.lesson6.room.db.Cat

class SqliteHelper(
    context: Context,
    fileName: String
) : SQLiteOpenHelper(context, fileName, null, DB_VERSION) {

    companion object {
        private const val DB_VERSION = 1

        private const val TABLE_NAME_CAT = "Cat"
        private const val COLUMN_CAT_ID = "id"
        private const val COLUMN_CAT_NAME = "name"
        private const val COLUMN_CAT_CREATED_AT = "created_at"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME_CAT($COLUMN_CAT_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_CAT_NAME TEXT NOT NULL, $COLUMN_CAT_CREATED_AT INTEGER NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun getAllCats(): List<Cat> {
        val result = mutableListOf<Cat>()
        val cursor = readableDatabase.query(
            TABLE_NAME_CAT,
            arrayOf(COLUMN_CAT_ID, COLUMN_CAT_NAME, COLUMN_CAT_CREATED_AT),
            null,
            null,
            null,
            null,
            "$COLUMN_CAT_ID ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                result.add(buildCatFromCursor(it))
            }
        }
        return result
    }

    fun getCatById(id: Int): Cat? {
        val cursor = readableDatabase.query(
            TABLE_NAME_CAT,
            arrayOf(COLUMN_CAT_ID, COLUMN_CAT_NAME, COLUMN_CAT_CREATED_AT),
            "$COLUMN_CAT_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        cursor.use {
            return if (it.moveToNext()) {
                buildCatFromCursor(it)
            } else {
                null
            }
        }
    }

    fun insertCat(cat: Cat) {
        val contentValues = ContentValues().apply {
            put(COLUMN_CAT_NAME, cat.name)
            put(COLUMN_CAT_CREATED_AT, cat.createdAt)
        }
        writableDatabase.insert(TABLE_NAME_CAT, null, contentValues)
    }

    //todo begin transaction
    fun insertAll(vararg cats: Cat) {
        cats.forEach { cat ->
            val cv = ContentValues().apply {
                put(COLUMN_CAT_NAME, cat.name)
                put(COLUMN_CAT_CREATED_AT, cat.createdAt)
            }
            writableDatabase.insert(TABLE_NAME_CAT, null, cv)
        }
    }

    fun deleteCat(id: Int) {
        writableDatabase.delete(TABLE_NAME_CAT, "$COLUMN_CAT_ID = ?", arrayOf(id.toString()))
    }

    private fun buildCatFromCursor(cursor: Cursor) =
        Cat(
            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CAT_ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAT_NAME)),
            cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CAT_CREATED_AT))
        )

}
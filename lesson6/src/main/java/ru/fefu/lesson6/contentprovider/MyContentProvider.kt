package ru.fefu.lesson6.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import ru.fefu.lesson6.sqlite.SqliteHelper

class MyContentProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "ru.fefu.lesson6"

        private const val CODE_ALL_CATS = 1
        private const val CODE_SINGLE_CAT = 2
    }

    private lateinit var dbHelper: SqliteHelper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, "cat", CODE_ALL_CATS)
        addURI(AUTHORITY, "cat/#", CODE_SINGLE_CAT)
    }

    override fun onCreate(): Boolean {
        dbHelper = SqliteHelper(context!!, "database")
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? =
        when (uriMatcher.match(uri)) {
            CODE_ALL_CATS -> dbHelper.allCatsCursor()
            CODE_SINGLE_CAT -> dbHelper.catByIdCursor(uri.lastPathSegment?.toInt() ?: 0)
            else -> throw IllegalArgumentException()
        }

    override fun getType(uri: Uri): String =
        when (uriMatcher.match(uri)) {
            CODE_ALL_CATS -> "vnd.android.cursor.dir/vnd.$AUTHORITY.cat"
            CODE_SINGLE_CAT -> "vnd.android.cursor.item/vnd.$AUTHORITY.cat"
            else -> throw IllegalArgumentException()
        }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw IllegalArgumentException()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw IllegalArgumentException()
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw IllegalArgumentException()
    }
}
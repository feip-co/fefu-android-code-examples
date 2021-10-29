package ru.fefu.lesson6.room.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Cat::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE Cat1 (id INTEGER PRIMARY KEY AUTOINCREMENT)")
        database.execSQL("ALTER TABLE Cat ADD COLUMN surname TEXT")
    }
}
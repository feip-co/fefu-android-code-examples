package ru.fefu.lesson6.room.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cat::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}
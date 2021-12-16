package ru.fefu.lesson6

import android.app.Application
import androidx.room.Room
import ru.fefu.lesson6.room.db.MIGRATION_1_2
import ru.fefu.lesson6.room.db.MyDatabase

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    val db: MyDatabase by lazy {
        Room.databaseBuilder(
            this,
            MyDatabase::class.java,
            "my_database"
        ).allowMainThreadQueries().addMigrations(MIGRATION_1_2).build()
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
    }

}
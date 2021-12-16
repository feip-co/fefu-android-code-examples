package ru.fefu.lesson6.room.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CatDao {

    @Query("SELECT * FROM cat")
    fun getAll(): LiveData<List<Cat>>

    @Query("SELECT * FROM cat WHERE id=:id")
    fun getById(id: Int): Cat

    @Insert
    fun insert(cat: Cat)

    @Insert
    fun insertAll(vararg cat: Cat)

    @Delete
    fun delete(cat: Cat)

}
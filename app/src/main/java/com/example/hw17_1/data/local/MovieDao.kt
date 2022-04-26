package com.example.hw17_1.data.local

import androidx.room.*
import com.example.hw17_1.model.DatabaseMovie
import com.example.hw17_1.model.MovieDetail

@Dao
interface MovieDao {
    @Insert
    fun insertMovieList(list: MutableList<DatabaseMovie>)
    @Query("DELETE FROM DatabaseMovie")
    fun deleteAll()

    @Transaction
    fun updateDatabase(list: MutableList<DatabaseMovie>) {
        deleteAll()
        insertMovieList(list)
    }
}
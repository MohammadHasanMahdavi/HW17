package com.example.hw17_1.data.local

import androidx.room.*
import com.example.hw17_1.model.Movie
import com.example.hw17_1.model.MovieList


@Dao
interface MovieDao {
    @Insert
    fun insertMovieList(list: MutableList<Movie>)
    @Query("DELETE FROM Movie")
    fun deleteAll()

    @Transaction
   suspend fun updateDatabase(list: MutableList<Movie>) {
        deleteAll()
        insertMovieList(list)
    }
}
package com.example.hw17_1.data.local

import com.example.hw17_1.model.Movie
import com.example.hw17_1.model.MovieList
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    suspend fun updateDatabase(list: MutableList<Movie>) {
        movieDao.updateDatabase(list)
    }
}
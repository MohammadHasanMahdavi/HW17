package com.example.hw17_1.data.local

import com.example.hw17_1.model.DatabaseMovie
import com.example.hw17_1.model.MovieDetail
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    fun updateDatabase(list: MutableList<DatabaseMovie>) {
        movieDao.updateDatabase(list)
    }
}
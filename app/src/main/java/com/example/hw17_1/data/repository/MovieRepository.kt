package com.example.hw17_1.data.repository

import android.util.Log
import com.example.hw17_1.data.local.LocalDataSource
import com.example.hw17_1.data.remote.RemoteSource
import com.example.hw17_1.model.MovieDetail
import com.example.hw17_1.model.MovieList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getPopularMovieList() = remoteSource.getPopularMovieList()
    suspend fun searchMovieList(query: String) = remoteSource.searchMovieList(query)
    suspend fun getUpcomingMovieList() = remoteSource.getUpcomingMovieList()
    suspend fun getMovieDetail(id: Int) = remoteSource.getMovieDetails(id)
}
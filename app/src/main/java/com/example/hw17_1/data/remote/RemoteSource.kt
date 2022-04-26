package com.example.hw17_1.data.remote

import com.example.hw17_1.model.Movie
import com.example.hw17_1.model.MovieDetail
import com.example.hw17_1.model.MovieList
import retrofit2.Response

interface RemoteSource {
    suspend fun getPopularMovieList() : Response<MovieList>
    suspend fun searchMovieList(query: String) : Response<MovieList>
    suspend fun getUpcomingMovieList() : Response<MovieList>
    suspend fun getMovieDetails(id:Int) : Response<MovieDetail>
}
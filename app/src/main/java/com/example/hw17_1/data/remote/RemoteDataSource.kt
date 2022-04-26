package com.example.hw17_1.data.remote

import com.example.hw17_1.model.Movie
import com.example.hw17_1.model.MovieDetail
import com.example.hw17_1.model.MovieList
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) : RemoteSource {
    override suspend fun getPopularMovieList() = apiService.getPopularMovieList(1)
    override suspend fun searchMovieList(query:String) = apiService.searchMovies(query)
    override suspend fun getMovieDetails(id:Int)=apiService.getMovieDetails(id)
    override suspend fun getUpcomingMovieList()=apiService.getUpcomingMovieList(15)
}
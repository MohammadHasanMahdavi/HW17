package com.example.hw17_1.data.remote

import com.example.hw17_1.model.Movie
import com.example.hw17_1.model.MovieDetail
import com.example.hw17_1.model.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovieList(@Query("page") page:Int) : Response<MovieList>

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query:String) : Response<MovieList>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovieList(@Query("page")page: Int) : Response<MovieList>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id")id:Int) : Response<MovieDetail>
}
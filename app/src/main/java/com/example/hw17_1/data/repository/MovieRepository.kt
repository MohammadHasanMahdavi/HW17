package com.example.hw17_1.data.repository

import android.util.Log
import com.example.hw17_1.data.local.LocalDataSource
import com.example.hw17_1.data.remote.RemoteSource
import com.example.hw17_1.model.Movie
import com.example.hw17_1.model.MovieList
import com.example.hw17_1.util.Resource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getPopularMovieList() : Resource <MutableList<Movie>> {
        val response : MutableList<Movie>
        return try {
            response = remoteSource.getPopularMovieList().body()!!.results
            coroutineScope {
                launch {
                    localDataSource.updateDatabase(response)
                }
            }
            Resource.success(response)
        }catch (e:IOException){
            Resource.Error(e)
        }
    }

    suspend fun searchMovieList(query: String) :Resource<MutableList<Movie>>{
        val response : MutableList<Movie>
        return try {
            response = remoteSource.searchMovieList(query).body()!!.results
            coroutineScope {
                launch {
                    localDataSource.updateDatabase(response)
                }
            }
            Resource.success(response)
        }catch (e:IOException){
            Resource.Error(e)
        }
    }

    suspend fun getUpcomingMovieList() :Resource<MutableList<Movie>>{
        val response : MutableList<Movie>
        return try {
            response = remoteSource.getUpcomingMovieList().body()!!.results
            coroutineScope {
                launch {
                    localDataSource.updateDatabase(response)
                }
            }
            Resource.success(response)
        }catch (e:IOException){
            Resource.Error(e)
        }
    }
    suspend fun getMovieDetail(id: Int) = remoteSource.getMovieDetails(id)
}
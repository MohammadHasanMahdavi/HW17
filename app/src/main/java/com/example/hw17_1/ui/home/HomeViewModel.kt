package com.example.hw17_1.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw17_1.data.repository.MovieRepository
import com.example.hw17_1.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository):ViewModel() {
    val popularMovieList : MutableLiveData<MutableList<Movie>> by lazy {
        MutableLiveData()
    }
    val searchResult = MutableStateFlow<MutableList<Movie>>(mutableListOf())
    val upcomingMovieList : MutableLiveData<MutableList<Movie>> by lazy {
        MutableLiveData()
    }




    fun searchMovieList(query:String) {
        viewModelScope.launch {
            val response = movieRepository.searchMovieList(query)
                if (response.isSuccessful) {
                    searchResult.value = response.body()?.results ?: mutableListOf()
                }
        }
    }

    fun getPopularMovieList() {
        viewModelScope.launch {
            val response = movieRepository.getPopularMovieList()
            if (response.isSuccessful){
                popularMovieList.postValue(response.body()?.results ?: mutableListOf())
            }
        }
    }

    fun getUpcomingMovieList() {
        viewModelScope.launch {
            val response = movieRepository.getUpcomingMovieList()
            if (response.isSuccessful){
                popularMovieList.postValue(response.body()?.results ?: mutableListOf())
            }
        }
    }
}
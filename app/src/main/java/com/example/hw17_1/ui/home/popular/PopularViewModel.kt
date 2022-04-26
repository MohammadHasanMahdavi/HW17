package com.example.hw17_1.ui.home.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw17_1.data.repository.MovieRepository
import com.example.hw17_1.model.Movie
import com.example.hw17_1.model.MovieList
import com.example.hw17_1.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {
    val popularMovieList = MutableStateFlow<Resource<MutableList<Movie>>>(Resource.Loading(
        mutableListOf()))

    fun getPopularMovieList() {
        viewModelScope.launch {
            val response = movieRepository.getPopularMovieList()
            popularMovieList.value = response
        }
    }
}
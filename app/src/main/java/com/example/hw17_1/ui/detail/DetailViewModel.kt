package com.example.hw17_1.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw17_1.data.repository.MovieRepository
import com.example.hw17_1.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel(){
    val movie : MutableLiveData<MovieDetail> by lazy {
        MutableLiveData()
    }
    fun getMovieDetails(id:Int) {
        viewModelScope.launch {
            val response = movieRepository.getMovieDetail(id)
            if (response.isSuccessful)
                movie.postValue(response.body()!!)
        }
    }
}
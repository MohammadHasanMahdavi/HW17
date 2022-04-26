package com.example.hw17_1.model

data class MovieList(
    val page: Int,
    val results: MutableList<Movie>,
    val total_pages: Int,
    val total_results: Int
)
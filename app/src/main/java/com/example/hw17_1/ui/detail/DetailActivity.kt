package com.example.hw17_1.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.hw17_1.R
import com.example.hw17_1.databinding.ActivityDetailBinding
import com.example.hw17_1.ui.play.PlayActivity
import com.example.hw17_1.util.MOVIE_ID
import com.example.hw17_1.util.MOVIE_TITLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val viewModel : DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val id = intent.getIntExtra(MOVIE_ID,0)
        val title = intent.getStringExtra(MOVIE_TITLE)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel.movie.observe(this){movie->
            binding.voteAverage.text = "Vote Average : ${movie.vote_average} / 10"
            binding.genre.text = "Genre : ${movie.genres.joinToString(" , ") { it.name }}"
            binding.overview.text = "Overview : ${movie.overview}"
            binding.language.text = "Language : ${movie.original_language}"
            binding.voteCount.text = "Vote Count : ${movie.vote_count}"
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/${movie.poster_path}")
                .placeholder(R.drawable.placeholder)
                .into(binding.moviePoster)
            binding.playButton.visibility = View.VISIBLE
        }
        binding.playButton.visibility = View.GONE
        binding.playButton.setOnClickListener {
            val intent= Intent  (this,PlayActivity::class.java)
            startActivity(intent)
        }
        viewModel.getMovieDetails(id)
    }
}
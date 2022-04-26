package com.example.hw17_1.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hw17_1.R
import com.example.hw17_1.model.Movie

class MovieAdapter(private val onCLick:(id:Int,title:String) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val movieList : MutableList<Movie> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MovieViewHolder ->{
                holder.bind(movieList[position])
                holder.itemView.setOnClickListener{
                    onCLick(movieList[position].id,movieList[position].title)
                }
            }
        }
    }
    override fun getItemCount() = movieList.size

    class MovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val title :TextView = itemView.findViewById(R.id.movie_title)
        private val overView : TextView = itemView.findViewById(R.id.movie_overView)
        private val poster : ImageView = itemView.findViewById(R.id.movie_poster)

        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie){
            title.text = "Title : ${movie.title}"
            overView.text = "Overview : ${movie.overview}"
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500/${movie.poster_path}")
                .placeholder(R.drawable.placeholder)
                .into(poster)
        }
    }

    fun submitList(movies:MutableList<Movie>){
        movieList.clear()
        movieList.addAll(movies)
    }
}
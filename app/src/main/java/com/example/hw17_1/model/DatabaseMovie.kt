package com.example.hw17_1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseMovie (
    @PrimaryKey() val id: Int,
    val overview: String,
    val poster_path: String)
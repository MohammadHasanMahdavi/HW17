package com.example.hw17_1.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hw17_1.model.DatabaseMovie
import com.example.hw17_1.model.Movie
import com.example.hw17_1.model.MovieDetail
import javax.inject.Inject

@Database(entities = [Movie::class], version = 1,exportSchema = true)
abstract class DataBase() :RoomDatabase() {
    abstract fun movieDao() : MovieDao
}
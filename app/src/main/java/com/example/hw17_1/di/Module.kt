package com.example.hw17_1.di

import android.content.Context
import androidx.room.Room
import com.example.hw17_1.data.local.DataBase
import com.example.hw17_1.data.local.LocalDataSource
import com.example.hw17_1.data.local.MovieDao
import com.example.hw17_1.data.remote.ApiService
import com.example.hw17_1.data.remote.RemoteDataSource
import com.example.hw17_1.data.remote.RemoteSource
import com.example.hw17_1.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun provideInterceptor() : Interceptor{
        return Interceptor {
            val request = it.request()
            val url = request.url().newBuilder()
                .addQueryParameter("api_key","c1559a7d14066bd237f26b00ec904fe8")
                .addQueryParameter("language","en-US")
                .build()
            val new = request.newBuilder()
                .url(url)
                .build()
            it.proceed(new)
        }
    }

    @Singleton
    @Provides
    fun provideHttpClient(interceptor: Interceptor) : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit) : ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService) : RemoteSource{
        return RemoteDataSource(apiService)
    }
    @Singleton
    @Provides
    fun provideLocalDataSource(movieDao: MovieDao) : LocalDataSource{
        return LocalDataSource(movieDao)
    }
    @Singleton
    @Provides
    fun provideMovieRepository(remoteDataSource: RemoteSource,localDataSource: LocalDataSource) : MovieRepository{
        return MovieRepository(remoteDataSource,localDataSource)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : DataBase{
        return Room.databaseBuilder(context,DataBase::class.java,"Movie-Db").build()
    }
    @Singleton
    @Provides
    fun provideMovieDao(dataBase: DataBase) : MovieDao{
        return dataBase.movieDao()
    }
}
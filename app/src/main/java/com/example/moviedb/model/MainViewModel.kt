package com.example.moviedb.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.moviedb.ApiService
import com.example.moviedb.Room.AppDatabase
import com.example.moviedb.Room.MovieDao
import com.example.moviedb.Room.MovieEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ViewModel Class
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDao = AppDatabase.getDatabase(application).movieDao()
    private val apiService = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    val movies: LiveData<List<MovieEntity>> = movieDao.getAllMovies()

    fun fetchMoviesIfNeeded() {
        viewModelScope.launch {
            try {
                val apiData = apiService.getMovies()
                val movies = apiData.results.map { movie ->
                    MovieEntity(
                        id = movie.id,
                        title = movie.title,
                        releaseDate = movie.releaseDate,
                        posterPath = movie.posterPath
                    )
                }
                movieDao.insertAll(movies)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "Failed to fetch movies: ${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
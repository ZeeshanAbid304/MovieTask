package com.example.moviedb.Room



import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieDetailDao {
    @Query("SELECT * FROM moviesdetail")
    fun getAllMovies(): LiveData<List<MovieEntityDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntityDetail>)

    @Query("DELETE FROM moviesdetail WHERE id = :movieId")
    suspend fun deleteMovieDetails(movieId: Int)

    @Query("SELECT * FROM moviesdetail WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntityDetail?
    @Update
    suspend fun update(movie: MovieEntityDetail)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: MovieEntityDetail)
}
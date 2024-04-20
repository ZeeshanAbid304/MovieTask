package com.example.moviedb.Room
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviedb.model.Image
import com.example.moviedb.model.VideoListDTO
import com.google.gson.annotations.SerializedName

@Entity(tableName = "moviesdetail")
data class MovieEntityDetail(
    @PrimaryKey
    val id: Int,
    val title: String,
    val releaseDate: String?,
    val posterPath: String?,
    val budget: Long,
    val originalTitle: String,
    val overview: String?,
    val revenue: Long
)

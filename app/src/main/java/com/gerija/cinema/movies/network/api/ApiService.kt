package com.gerija.cinema.movies.network.api

import com.gerija.cinema.movies.network.model.DetailsContainer
import com.gerija.cinema.movies.network.model.MoviesContainerDto
import com.gerija.cinema.movies.network.model.ResultMovies
import com.gerija.cinema.movies.network.model.VideoContainer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getTopMovies(
        @Query("api_key") api_key: String = KEY,
        @Query("language") language: String = "ru"
    ): Call<MoviesContainerDto>

    @GET("movie/{movie_id}")
    fun getMoviesDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") api_key: String = KEY,
        @Query("language") language: String = "ru"
    ): Call<DetailsContainer>

    @GET("movie/{movie_id}/{videos}")
    fun getVideo(
        @Path("movie_id") id: Int,
        @Path("videos") videos: String = "videos",
        @Query("api_key") api_key: String = "d9ca6800f61b80298e342b05311fea59",
        @Query("language") language: String = "ru"
    ): Call<VideoContainer>

    companion object {

        private const val KEY = "d9ca6800f61b80298e342b05311fea59"

    }
}
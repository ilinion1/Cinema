package com.gerija.cinema.movies.network.api

import com.gerija.cinema.movies.network.model.MoviesContainerDto
import com.gerija.cinema.movies.network.model.Test
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular$KEY")
    fun getTopMovies(
        @Query("language") language: String = "ru"
    ): Call<MoviesContainerDto>

    @GET("movie/{movie_id}/{videos}")
    fun getDescriptionMovies(
        @Path("movie_id") id: String,
        @Path("videos") videos: String = "videos",
        @Query("api_key") api_key: String = "d9ca6800f61b80298e342b05311fea59",
        @Query("language") language: String = "ru"
    ): Call<Test>

    companion object {

        private const val KEY = "?api_key=d9ca6800f61b80298e342b05311fea59"


    }
}
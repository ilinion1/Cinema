package com.gerija.cinema.movies.network.model

data class VideoContainer(
    val id: Int,
    val results: List<ResultMovies>
)
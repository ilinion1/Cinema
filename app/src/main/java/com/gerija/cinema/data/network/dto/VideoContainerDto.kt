package com.gerija.cinema.data.network.dto

data class VideoContainerDto(
    val id: Int,
    val results: List<ResultMoviesDto>
)
package com.gerija.cinema.model.network.dto

data class VideoContainerDto(
    val id: Int,
    val results: List<ResultMoviesDto>
)
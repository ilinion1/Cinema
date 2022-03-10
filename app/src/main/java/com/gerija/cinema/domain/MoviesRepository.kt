package com.gerija.cinema.domain

import com.gerija.cinema.data.network.dto.MoviesContainerDto


interface MoviesRepository {

    suspend fun getTopMovies():Result<MoviesContainerDto>
}
package com.gerija.cinema.domain

import com.gerija.cinema.model.network.dto.ResultsDto

interface MoviesRepository {

    fun getTopMovies():List<ResultsDto>
}
package com.gerija.cinema.movies.network.reposiroty

import com.gerija.cinema.movies.network.model.ResultsDto

interface MoviesRepository {

    fun getTopMovies():List<ResultsDto>
}
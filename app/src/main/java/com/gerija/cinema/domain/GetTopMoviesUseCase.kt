package com.gerija.cinema.domain

class GetTopMoviesUseCase (private val repository: MoviesRepository) {


    suspend operator fun invoke() = repository.getTopMovies()
}
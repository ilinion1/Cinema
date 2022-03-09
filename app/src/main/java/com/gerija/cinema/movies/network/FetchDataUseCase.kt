package com.gerija.cinema.movies.network

import com.gerija.cinema.movies.network.api.ApiService
import com.gerija.cinema.movies.network.model.MoviesContainerDto

class FetchDataUseCase (private val apiService: ApiService) {
    suspend fun getMovies(): Result<MoviesContainerDto> {
        return try {
            Result.success(apiService.getTopMovies())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
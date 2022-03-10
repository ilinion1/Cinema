package com.gerija.cinema.domain

import com.gerija.cinema.model.network.api.ApiService
import com.gerija.cinema.model.network.dto.MoviesContainerDto

class FetchDataUseCase (private val apiService: ApiService) {
    suspend fun getMovies(): Result<MoviesContainerDto> {
        return try {
            Result.success(apiService.getTopMovies())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
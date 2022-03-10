package com.gerija.cinema.data.repository

import com.gerija.cinema.domain.MoviesRepository
import com.gerija.cinema.data.network.api.ApiService
import com.gerija.cinema.data.network.dto.MoviesContainerDto

class MoviesRepositoryImpl(private val apiService: ApiService) : MoviesRepository {

    override suspend fun getTopMovies(): Result<MoviesContainerDto> {
        return try {
            Result.success(apiService.getTopMovies())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
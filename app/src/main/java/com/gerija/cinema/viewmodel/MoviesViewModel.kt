package com.gerija.cinema.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gerija.cinema.data.network.api.ApiFactory
import com.gerija.cinema.domain.GetTopMoviesUseCase
import com.gerija.cinema.data.network.dto.MoviesContainerDto
import com.gerija.cinema.data.repository.MoviesRepositoryImpl
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {

    private val repository = MoviesRepositoryImpl(ApiFactory.create())
    private val fetchDataUseCase = GetTopMoviesUseCase(repository)
    private val _state = MutableLiveData<MoviesContainerDto>()
    val state: LiveData<MoviesContainerDto> get() = _state

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        viewModelScope.launch {
            fetchDataUseCase()
                .onSuccess { _state.value = it }
                .onFailure { _error.value = it.localizedMessage ?: "Error" }
        }
    }
}

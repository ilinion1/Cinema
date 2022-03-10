package com.gerija.cinema.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gerija.cinema.model.network.api.ApiFactory
import com.gerija.cinema.domain.FetchDataUseCase
import com.gerija.cinema.model.network.dto.MoviesContainerDto
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {

    private val fetchDataUseCase = FetchDataUseCase(ApiFactory.create())
    private val _state = MutableLiveData<MoviesContainerDto>()
    val state: LiveData<MoviesContainerDto> get() = _state

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        viewModelScope.launch {
            fetchDataUseCase.getMovies()
                .onSuccess { _state.value = it }
                .onFailure { _error.value = it.localizedMessage ?: "Error" }
        }
    }
}

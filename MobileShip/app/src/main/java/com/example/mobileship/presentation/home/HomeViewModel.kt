package com.example.mobileship.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileship.data.local.UserPreferences
import com.example.mobileship.domain.usecase.RouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для главного экрана
 * Загружает маршрут и данные пользователя
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val routeUseCase: RouteUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun setCabinNumber(cabinNumber: Int) {
        _uiState.update { currentState ->
            currentState.copy(cabinNumber = cabinNumber)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            routeUseCase().collect { routeList ->
                _uiState.update { currentState ->
                    currentState.copy(
                        route = routeList,
                        isLoading = false
                    )
                }
            }
        }
    }
}
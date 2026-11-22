package com.example.mobileship.presentation.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileship.domain.repository.CityInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана со списком городов
 * Загружает данные из репозитория и управляет состоянием экрана
 */
@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val cityInfoRepository: CityInfoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CitiesUiState())
    val uiState: StateFlow<CitiesUiState> = _uiState.asStateFlow()

    init {
        loadCities()
    }

    /**
     * Загружает список городов из репозитория
     * Обрабатывает состояния загрузки и ошибки
     */
    private fun loadCities() {
        viewModelScope.launch {
            cityInfoRepository.getCities()
                .onStart {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        error = null
                    )
                }
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Не удалось загрузить города"
                    )
                }
                .collect { cities ->
                    _uiState.value = CitiesUiState(
                        cities = cities,
                        isLoading = false
                    )
                }
        }
    }
}
package com.example.mobileship.presentation.cities

import com.example.mobileship.domain.model.CityInfo

/**
 * Состояние экрана со списком городов
 * Содержит список городов, статус загрузки и возможную ошибку
 * @property cities Список городов для отображения
 * @property isLoading Флаг загрузки данных
 * @property error Текст ошибки если она произошла
 */
data class CitiesUiState(
    val cities: List<CityInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
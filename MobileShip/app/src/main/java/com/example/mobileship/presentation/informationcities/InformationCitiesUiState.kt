package com.example.mobileship.presentation.informationcities

import com.example.mobileship.domain.model.CityInfo

/**
 * Состояние экрана с подробной информацией о городе
 * Содержит данные о городе, статус загрузки и возможную ошибку
 * @property city Объект с информацией о городе
 * @property isLoading Флаг загрузки данных
 * @property error Текст ошибки если она произошла
 */
data class InformationCitiesUiState(
    val city: CityInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
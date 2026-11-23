package com.example.mobileship.presentation.statuscabins

import com.example.mobileship.domain.model.CabinStatus

/**
 * Состояние экрана статусы кабин
 * Содержит список кают, статус загрузки и палубу
 * @property cabins Список кают
 * @property isLoading Флаг загрузки данных
 * @property selectedDeck Номер палубы
 */
data class StatusCabinUiState(
    val cabins: List<CabinStatus> = emptyList(),
    val selectedDeck: Int? = null,
    val isLoading: Boolean = true
)

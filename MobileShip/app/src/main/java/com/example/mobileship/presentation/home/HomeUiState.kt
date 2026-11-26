package com.example.mobileship.presentation.home

import com.example.mobileship.domain.model.CityNavigate

/**
 * Состояние главного экрана
 * Содержит данные о каюте пользователя и маршруте круиза
 */
data class HomeUiState(
    val cabinNumber: Int = 0,
    val userRole: String = "пассажир",
    val route: List<CityNavigate> = emptyList(),
    val isLoading: Boolean = false
)
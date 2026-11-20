package com.example.mobileship.presentation.cities

import com.example.mobileship.domain.model.CityInfo

data class CitiesUiState(
    val cities: List<CityInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
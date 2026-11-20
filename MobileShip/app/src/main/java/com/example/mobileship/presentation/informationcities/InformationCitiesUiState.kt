package com.example.mobileship.presentation.informationcities

import com.example.mobileship.domain.model.CityInfo

data class InformationCitiesUiState(
    val city: CityInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
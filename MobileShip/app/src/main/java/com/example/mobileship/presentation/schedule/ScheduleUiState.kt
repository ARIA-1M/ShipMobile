package com.example.mobileship.presentation.schedule

import com.example.mobileship.domain.model.Schedule

data class ScheduleUiState(
    val events: List<Schedule> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
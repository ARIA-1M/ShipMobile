package com.example.mobileship.presentation.schedule

import com.example.mobileship.domain.model.Schedule

/**
 * Состояние экрана расписания
 * Содержит список мероприятий, статус загрузки и возможную ошибку
 * @property events Список мероприятий на день
 * @property isLoading Флаг загрузки данных
 * @property error Текст ошибки если она произошла
 */
data class ScheduleUiState(
    val events: List<Schedule> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
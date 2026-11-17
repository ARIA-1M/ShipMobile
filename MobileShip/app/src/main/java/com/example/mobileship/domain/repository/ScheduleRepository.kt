package com.example.mobileship.domain.repository

import com.example.mobileship.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для работы с расписанием мероприятий
 */
interface ScheduleRepository {

    /**
     * Получает список всех мероприятий на текущий день
     * @return Flow со списком мероприятий
     */
    fun getSchedule(): Flow<List<Schedule>>
}
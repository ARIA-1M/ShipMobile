package com.example.mobileship.domain.usecase

import com.example.mobileship.domain.model.Schedule
import com.example.mobileship.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * Use Case для получения расписания на текущий день
 * Вычисляет какое мероприятие идет прямо сейчас
 */
class CurrentScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {

    /**
     * Получает расписание и отмечает текущие мероприятия
     * @return Flow со списком мероприятий с флагом isNow
     */
    operator fun invoke(): Flow<List<Schedule>> {
        return repository.getSchedule().map { events ->
            val now = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

            events.map { event ->
                val isNow = now >= event.startTime && now <= event.endTime
                event.copy(isNow = isNow)
            }
        }
    }
}
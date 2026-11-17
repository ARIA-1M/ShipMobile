package com.example.mobileship.data.local.schedule

import com.example.mobileship.data.remote.dto.ScheduleDto
import com.example.mobileship.domain.model.Schedule

/**
 * Маппер для преобразования данных модели расписания
 */
fun ScheduleDto.toDomain(): Schedule {
    return Schedule(
        id = idSchedule.toString(),
        title = title,
        startTime = startTime,
        endTime = endTime,
        location = location,
        isNow = false
    )
}
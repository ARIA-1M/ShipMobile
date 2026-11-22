package com.example.mobileship.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO-модель мероприятия из расписания
 */
@Serializable
data class ScheduleDto(
    val idSchedule: Int,
    val title: String,
    val date: String,
    val startTime: String,
    val endTime: String,

    @SerialName("locationTitle")
    val location: String
)
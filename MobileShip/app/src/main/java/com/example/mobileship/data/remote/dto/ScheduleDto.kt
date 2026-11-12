package com.example.mobileship.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDto(
    val idSchedule: Int,
    val title: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val location: String
)
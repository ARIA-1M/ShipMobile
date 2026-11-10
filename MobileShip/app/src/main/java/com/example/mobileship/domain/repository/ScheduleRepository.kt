package com.example.mobileship.domain.repository

import com.example.mobileship.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getSchedule(): Flow<List<Schedule>>
}
package com.example.mobileship.data.local.schedule

import com.example.mobileship.data.remote.ShipApi
import com.example.mobileship.data.remote.safeApiCall
import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.model.Schedule
import com.example.mobileship.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Реализация репозитория для работы с расписанием
 * Отправляет запросы к api и возвращает ответы
 */
class ScheduleRepositoryImpl @Inject constructor(private val api: ShipApi) : ScheduleRepository {
    override fun getSchedule(): Flow<List<Schedule>> = flow {
        when (val result = safeApiCall { api.getSchedule() }) {
            is DomainResult.Success -> emit(result.data.map { it.toDomain() })
            is DomainResult.Error -> emit(emptyList())
        }
    }
}


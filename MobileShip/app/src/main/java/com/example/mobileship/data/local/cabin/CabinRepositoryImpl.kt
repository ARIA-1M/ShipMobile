package com.example.mobileship.data.local.cabin

import com.example.mobileship.data.remote.ShipApi
import com.example.mobileship.data.remote.safeApiCall
import com.example.mobileship.domain.model.CabinStatus
import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.repository.CabinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Реализация репозитория для работы со статусами кают
 * Отправляет запросы к api и возвращает ответы
 */
class CabinRepositoryImpl @Inject constructor(private val api: ShipApi) : CabinRepository {
    override fun getCabinForCleaning(): Flow<List<CabinStatus>> = flow {
        when (val result = safeApiCall { api.getCabins() }) {
            is DomainResult.Success -> emit(result.data.data.map { it.toDomain() })
            is DomainResult.Error -> emit(emptyList())
        }
    }

    override fun getActiveAlarms(): Flow<List<CabinStatus>> = flow {
        when (val result = safeApiCall { api.getCabins() }) {
            is DomainResult.Success -> {
                val alarms = result.data.data.map { it.toDomain() }
                    .filter { it.status == "sos" || it.status == "тревога" }
                emit(alarms)
            }
            is DomainResult.Error -> emit(emptyList())
        }
    }
}
package com.example.mobileship.data.local.cabin

import android.util.Log
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
        Log.d("CABIN_API", "Отправляем запрос: GET api/cabin/alarms")

        when (val result = safeApiCall { api.getAlarms() }) {
            is DomainResult.Success -> {
                Log.d("CABIN_API", "Успешный ответ: ${result.data}")

                val alarms = result.data.map { it.toDomain() }

                Log.d("CABIN_API", "Преобразованные тревоги: $alarms")

                emit(alarms)
            }

            is DomainResult.Error -> {
                Log.e("CABIN_API", "Ошибка запроса: ${result.message}")
                emit(emptyList())
            }
        }
    }
}
package com.example.mobileship.domain.repository

import com.example.mobileship.domain.model.CityInfo
import com.example.mobileship.domain.model.DomainResult
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для получения информации о городах
 */
interface CityInfoRepository {

    /**
     * Получает список всех городов
     * @return Flow со списком информации о городах
     */
    fun getCities(): Flow<List<CityInfo>>

    /**
     * Получает подробную информацию о конкретном городе
     * @param cityId Уникальный идентификатор города
     * @return DomainResult с информацией о городе или сообщением об ошибке
     */
    suspend fun getDetailsCities(cityId: String): DomainResult<CityInfo>
}
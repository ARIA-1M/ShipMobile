package com.example.mobileship.domain.repository

import com.example.mobileship.domain.model.CityNavigate
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для работы с навигацией по маршруту
 */
interface CityNavigationRepository {

    /**
     * Получает список всех точек маршрута с их текущими статусами
     * @return Flow со списком точек маршрута
     */
    fun getRoute(): Flow<List<CityNavigate>>

    /**
     * Обновляет текущее состояние навигации
     * Пересчитывает статусы городов на основе текущего времени или положения
     */
    suspend fun updateNavigationState()
}
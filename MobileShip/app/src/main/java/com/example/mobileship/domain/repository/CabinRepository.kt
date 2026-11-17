package com.example.mobileship.domain.repository

import com.example.mobileship.domain.model.CabinStatus
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для работы с данными о каютах
 */
interface CabinRepository {

    /**
     * Получает список кают, которые требуют уборки или помечены как не беспокоить
     * @return Flow со списком статусов кают
     */
    fun getCabinForCleaning(): Flow<List<CabinStatus>>

    /**
     * Получает список кают с активными сигналами тревоги
     * @return Flow со списком кают, где сработала тревога
     */
    fun getActiveAlarms(): Flow<List<CabinStatus>>
}
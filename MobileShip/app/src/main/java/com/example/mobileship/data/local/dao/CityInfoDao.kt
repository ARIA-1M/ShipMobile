package com.example.mobileship.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.mobileship.data.local.entity.CityInfoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для работы с локальной таблицей карточек городов
 */
@Dao
interface CityInfoDao {

    /**
     * Получает список всех городов
     * @return Flow со списком CityInfoEntity
     */
    @Query("SELECT * FROM city_info")
    fun getCities(): Flow<List<CityInfoEntity>>

    /**
     * Сохраняет список городов в кеш
     * @param cities Список городов
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityInfoEntity>)

    /**
     * Очищает таблицу городов
     */
    @Query("DELETE FROM city_info")
    suspend fun clearCities()
}
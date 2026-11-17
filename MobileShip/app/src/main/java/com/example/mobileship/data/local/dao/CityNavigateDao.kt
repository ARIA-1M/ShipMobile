package com.example.mobileship.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.mobileship.data.local.entity.CityNavigateEntity
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для работы с локальной таблицей навигации по маршруту
 */
@Dao
interface CityNavigateDao {

    /**
     * Получает список точек маршрута
     * @return Flow со списком CityNavigateEntity
     */
    @Query("SELECT * FROM city_navigate")
    fun getRoute(): Flow<List<CityNavigateEntity>>

    /**
     * Сохраняет маршрут в базу
     * @param route Список точек маршрута
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: List<CityNavigateEntity>)

    /**
     * Очищает таблицу маршрута
     */
    @Query("DELETE FROM city_navigate")
    suspend fun clearRoute()
}
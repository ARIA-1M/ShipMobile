package com.example.mobileship.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.mobileship.data.local.entity.ScheduleCacheEntity
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для работы с локальным кешем расписания
 */
@Dao
interface ScheduleDao {

    /**
     * Получает расписание из кеша
     * @return Flow со списком мероприятий
     */
    @Query("SELECT * FROM schedule_cache")
    fun getSchedule(): Flow<List<ScheduleCacheEntity>>

    /**
     * Сохраняет расписание в кеш
     * @param schedule Список мероприятий
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: List<ScheduleCacheEntity>)

    /**
     * Очищает кеш расписания
     */
    @Query("DELETE FROM schedule_cache")
    suspend fun clearSchedule()
}
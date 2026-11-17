package com.example.mobileship.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.mobileship.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для работы с локальной таблицей пользователя
 */
@Dao
interface UserDao {

    /**
     * Получает данные текущего пользователя
     * @return Flow с объектом UserEntity или null
     */
    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): Flow<UserEntity?>

    /**
     * Сохраняет данные пользователя
     * @param user Объект для сохранения
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    /**
     * Очищает таблицу пользователя
     */
    @Query("DELETE FROM user")
    suspend fun clearUser()
}
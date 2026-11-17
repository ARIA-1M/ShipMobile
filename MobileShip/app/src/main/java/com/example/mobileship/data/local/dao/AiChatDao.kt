package com.example.mobileship.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.example.mobileship.data.local.entity.AiChatEntity
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для работы с локальной историей чата с ИИ
 */
@Dao
interface AiChatDao {

    /**
     * Получает последние 100 сообщений чата
     * @return Flow со списком сообщений
     */
    @Query("SELECT * FROM ai_chat ORDER BY createdAt ASC LIMIT 100")
    fun getChatHistory(): Flow<List<AiChatEntity>>

    /**
     * Добавляет новое сообщение в историю
     * @param message Объект сообщения
     */
    @Insert
    suspend fun insertMessage(message: AiChatEntity)

    /**
     * Очищает историю чата
     */
    @Query("DELETE FROM ai_chat")
    suspend fun clearChat()
}
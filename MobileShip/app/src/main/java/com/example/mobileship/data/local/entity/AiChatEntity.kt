package com.example.mobileship.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import java.util.Date

/**
 * Локальная сущность для хранения истории сообщений ИИ-чата
 * Храним последние 100 сообщений на устройстве
 */
@Entity(tableName = "ai_chat")
data class AiChatEntity(
    @PrimaryKey(autoGenerate = true) val idMessage: Int = 0,
    val message: String,
    val role: String
)
package com.example.mobileship.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

/**
 * Локальная сущность для хранения данных текущего пользователя
 */
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val idUser: String,
    val role: String,
    val cabinNumber: Int?
)
package com.example.mobileship.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.mobileship.data.local.dao.*
import com.example.mobileship.data.local.entity.*

/**
 * Главный класс локальной базы данных Room
 * Объединяет все сущности и предоставляет доступ к DAO
 */
@Database(
    entities = [
        ScheduleCacheEntity::class,
        UserEntity::class,
        AiChatEntity::class,
        CityInfoEntity::class,
        CityNavigateEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
    abstract fun userDao(): UserDao
    abstract fun aiChatDao(): AiChatDao
    abstract fun cityInfoDao(): CityInfoDao
    abstract fun cityNavigateDao(): CityNavigateDao
}
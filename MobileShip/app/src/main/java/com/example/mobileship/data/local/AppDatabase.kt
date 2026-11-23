package com.example.mobileship.data.local

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mobileship.data.local.dao.*
import com.example.mobileship.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    /**
     * Доступ к таблице кеша расписания
     */
    abstract fun scheduleDao(): ScheduleDao

    /**
     * Доступ к таблице пользователя
     */
    abstract fun userDao(): UserDao

    /**
     * Доступ к таблице истории чата
     */
    abstract fun aiChatDao(): AiChatDao

    /**
     * Доступ к таблице информации о городах
     */
    abstract fun cityInfoDao(): CityInfoDao

    /**
     * Доступ к таблице навигации по маршруту
     */
    abstract fun cityNavigateDao(): CityNavigateDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Возвращает единственный экземпляр базы данных (Singleton)
         * При первом создании заполняет таблицу навигации городами
         * @param context Контекст приложения
         * @return Экземпляр AppDatabase
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ship_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override suspend fun onCreate(connection: SQLiteConnection) {
                            withContext(Dispatchers.IO) {
                                val database = getDatabase(context)
                            }
                        }
                    }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
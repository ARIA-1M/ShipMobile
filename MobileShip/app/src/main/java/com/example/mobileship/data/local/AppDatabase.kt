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

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

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
                                val cities = listOf(
                                    CityNavigateEntity("1", "Нижний Новгород", "passed"),
                                    CityNavigateEntity("2", "Кострома", "passed"),
                                    CityNavigateEntity("3", "Ярославль", "passed"),
                                    CityNavigateEntity("4", "Углич", "passed"),
                                    CityNavigateEntity("5", "Москва", "current"),
                                    CityNavigateEntity("6", "Константиново", "upcoming"),
                                    CityNavigateEntity("7", "Рязань", "upcoming"),
                                    CityNavigateEntity("8", "Касимов", "upcoming"),
                                    CityNavigateEntity("9", "Муром", "upcoming"),
                                    CityNavigateEntity("10", "Павлово", "upcoming"),
                                    CityNavigateEntity("11", "Нижний Новгород", "upcoming")
                                )
                                database.cityNavigateDao().insertRoute(cities)
                            }
                        }
                    }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
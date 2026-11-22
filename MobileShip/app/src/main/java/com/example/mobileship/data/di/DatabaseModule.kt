package com.example.mobileship.data.di

import android.content.Context
import androidx.room3.Room
import com.example.mobileship.data.local.AppDatabase
import com.example.mobileship.data.local.UserPreferences
import com.example.mobileship.data.local.dao.*
import com.example.mobileship.data.repository.CityInfoRepositoryImpl
import com.example.mobileship.data.repository.CityNavigationRepositoryImpl
import com.example.mobileship.domain.repository.CityInfoRepository
import com.example.mobileship.domain.repository.CityNavigationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Модуль Hilt для предоставления зависимостей локального хранилища
 * Отвечает за создание базы данных Room и репозиториев
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Создает и предоставляет экземпляр базы данных Room
     * @param context Контекст приложения
     * @return Экземпляр AppDatabase
     */
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ship_database"
        ).build()
    }

    /**
     * Предоставляет DAO для работы с расписанием
     * @param db Экземпляр базы данных
     * @return Реализация ScheduleDao
     */
    @Provides
    fun provideScheduleDao(db: AppDatabase): ScheduleDao = db.scheduleDao()

    /**
     * Предоставляет DAO для работы с пользователем
     * @param db Экземпляр базы данных
     * @return Реализация UserDao
     */
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    /**
     * Предоставляет DAO для работы с историей чата
     * @param db Экземпляр базы данных
     * @return Реализация AiChatDao
     */
    @Provides
    fun provideAiChatDao(db: AppDatabase): AiChatDao = db.aiChatDao()

    /**
     * Предоставляет DAO для работы с городами
     * @param db Экземпляр базы данных
     * @return Реализация CityInfoDao
     */
    @Provides
    fun provideCityInfoDao(db: AppDatabase): CityInfoDao = db.cityInfoDao()

    /**
     * Предоставляет DAO для работы с навигацией
     * @param db Экземпляр базы данных
     * @return Реализация CityNavigateDao
     */
    @Provides
    fun provideCityNavigateDao(db: AppDatabase): CityNavigateDao = db.cityNavigateDao()

    /**
     * Предоставляет реализацию репозитория навигации
     * @param dao DAO для работы с городами маршрута
     * @return Реализация CityNavigationRepository
     */
    @Provides
    @Singleton
    fun provideCityNavigationRepository(
        dao: CityNavigateDao
    ): CityNavigationRepository {
        return CityNavigationRepositoryImpl(dao)
    }

    /**
     * Предоставляет реализацию репозитория информации о городах
     * @param dao DAO для работы с карточками городов
     * @return Реализация CityInfoRepository
     */
    @Provides
    @Singleton
    fun provideCityInfoRepository(
        dao: CityInfoDao
    ): CityInfoRepository {
        return CityInfoRepositoryImpl(dao)
    }

    /**
     * Предоставляет экземпляр для работы с настройками пользователя
     * @param context Контекст приложения
     * @return Экземпляр UserPreferences
     */
    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context
    ): UserPreferences {
        return UserPreferences(context)
    }
}
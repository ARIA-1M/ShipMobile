
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
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Создает и предоставляет экземпляр базы данных Room
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

    @Provides
    fun provideScheduleDao(db: AppDatabase): ScheduleDao = db.scheduleDao()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideAiChatDao(db: AppDatabase): AiChatDao = db.aiChatDao()

    @Provides
    fun provideCityInfoDao(db: AppDatabase): CityInfoDao = db.cityInfoDao()

    @Provides
    fun provideCityNavigateDao(db: AppDatabase): CityNavigateDao = db.cityNavigateDao()

    @Provides
    @Singleton
    fun provideCityNavigationRepository(
        dao: CityNavigateDao
    ): CityNavigationRepository {
        return CityNavigationRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideCityInfoRepository(
        dao: CityInfoDao
    ): CityInfoRepository {
        return CityInfoRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context
    ): UserPreferences {
        return UserPreferences(context)
    }
}

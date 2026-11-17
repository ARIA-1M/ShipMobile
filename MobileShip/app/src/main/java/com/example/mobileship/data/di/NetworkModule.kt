package com.example.mobileship.data.di

import com.example.mobileship.data.local.airesponse.AiRepositoryImpl
import com.example.mobileship.data.local.cabin.CabinRepositoryImpl
import com.example.mobileship.data.local.schedule.ScheduleRepositoryImpl
import com.example.mobileship.data.remote.ShipApi
import com.example.mobileship.data.remote.createLoggingInterceptor
import com.example.mobileship.domain.repository.AiRepository
import com.example.mobileship.domain.repository.CabinRepository
import com.example.mobileship.domain.repository.ScheduleRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Модуль Hilt для настройки сетевого слоя и предоставления зависимостей
 * Отвечает за создание Retrofit и реализаций репозиториев
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://shipapi.runasp.net/"

    /**
     * Создает и настраивает HTTP-клиент
     * Добавляет перехватчик логирования и устанавливает таймауты соединения
     * @return Настроенный экземпляр OkHttpClient
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Создает и настраивает клиент Retrofit
     * @param okHttpClient HTTP-клиент с таймаутами и логированием
     * @return Настроенный экземпляр Retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    /**
     * Создает реализацию интерфейса ShipApi на основе Retrofit
     * @param retrofit Настроенный экземпляр Retrofit
     * @return Экземпляр ShipApi для выполнения сетевых запросов
     */
    @Provides
    @Singleton
    fun provideShipApi(retrofit: Retrofit): ShipApi = retrofit.create(ShipApi::class.java)

    /**
     * Предоставляет реализацию репозитория расписания
     * @param api Экземпляр ShipApi для сетевых запросов
     * @return Реализация ScheduleRepository
     */
    @Provides
    @Singleton
    fun provideScheduleRepository(api: ShipApi): ScheduleRepository = ScheduleRepositoryImpl(api)

    /**
     * Предоставляет реализацию репозитория кают
     * @param api Экземпляр ShipApi для сетевых запросов
     * @return Реализация CabinRepository
     */
    @Provides
    @Singleton
    fun provideCabinRepository(api: ShipApi): CabinRepository = CabinRepositoryImpl(api)

    /**
     * Предоставляет реализацию репозитория для работы с ИИ
     * @param api Экземпляр ShipApi для сетевых запросов
     * @return Реализация AiRepository
     */
    @Provides
    @Singleton
    fun provideAiRepository(api: ShipApi): AiRepository = AiRepositoryImpl(api)
}
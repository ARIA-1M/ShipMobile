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
 * 
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://shipapi.runasp.net/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            // Оставляем только логирование
            .addInterceptor(createLoggingInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideShipApi(retrofit: Retrofit): ShipApi = retrofit.create(ShipApi::class.java)

    @Provides
    @Singleton
    fun provideScheduleRepository(api: ShipApi): ScheduleRepository = ScheduleRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideCabinRepository(api: ShipApi): CabinRepository = CabinRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideAiRepository(api: ShipApi): AiRepository = AiRepositoryImpl(api)
}
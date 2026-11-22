package com.example.mobileship.data.remote

import okhttp3.logging.HttpLoggingInterceptor

/**
 * Создает перехватчик для логирования сетевых запросов и ответов
 * @return Настроенный HttpLoggingInterceptor
 */
fun createLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
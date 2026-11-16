package com.example.mobileship.data.remote

import okhttp3.logging.HttpLoggingInterceptor

fun createLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
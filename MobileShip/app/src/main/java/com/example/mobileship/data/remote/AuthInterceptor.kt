package com.example.mobileship.data.remote
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

// Перехватчик для добавления токена
class AuthInterceptor(private var token: String? = null) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(request.build())
    }

    fun updateToken(newToken: String) {
        token = newToken
    }
}

fun createLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
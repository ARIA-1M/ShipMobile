package com.example.mobileship.data.remote

import com.example.mobileship.domain.model.DomainResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): DomainResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            DomainResult.Success(apiCall())
        } catch (e: SocketTimeoutException) {
            DomainResult.Error("Превышено время ожидания. Проверьте интернет.")
        } catch (e: IOException) {
            DomainResult.Error("Нет подключения к интернету.")
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> DomainResult.Error("Ошибка авторизации (401)")
                404 -> DomainResult.Error("Данные не найдены (404)")
                500 -> DomainResult.Error("Ошибка на сервере (500)")
                else -> DomainResult.Error("Ошибка сети: ${e.code()}")
            }
        } catch (e: Exception) {
            DomainResult.Error(e.message ?: "Неизвестная ошибка")
        }
    }
}
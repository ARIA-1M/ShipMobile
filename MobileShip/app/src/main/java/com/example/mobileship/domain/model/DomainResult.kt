package com.example.mobileship.domain.model

/**
 * Класс для представления результата операции
 * Позволяет вернуть либо успешные данные либо сообщение об ошибке
 */
sealed class DomainResult<out T> {
    data class Success<T>(val data: T) : DomainResult<T>()
    data class Error(val message: String) : DomainResult<Nothing>()
}
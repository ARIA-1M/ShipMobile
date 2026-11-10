package com.example.mobileship.domain.model

sealed class DomainResult<out T> {
    data class Success<T>(val data: T) : DomainResult<T>()
    data class Error(val message: String) : DomainResult<Nothing>()
}
package com.example.mobileship.domain.hardware

/**
 * Интерфейс для сканирования QR-кодов
 * Позволяет подменять реальный сканер на фейковый в тестах
 */
interface CameraPermissionChecker {
    fun hasPermission(): Boolean
}
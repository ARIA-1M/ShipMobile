package com.example.mobileship.domain.hardware

/**
 * Интерфейс для сканирования QR-кодов
 * Позволяет подменять реальный сканер на фейковый в тестах
 */
interface QrScanner {

    /**
     * Запускает сканирование и возвращает результат
     * @param onQrCodeScanned Колбэк, вызываемый при успешном сканировании
     */
    fun startScanning(onQrCodeScanned: (String) -> Unit)
}
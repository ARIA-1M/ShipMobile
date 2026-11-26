package com.example.mobileship.domain.hardware

/**
 * Интерфейс для управления звуковыми сигналами тревог
 * Позволяет подменять реальный звуковой сигнал на фейковый в тестах
 */
interface AlarmSoundManager {

    fun startAlarm()

    fun stopAlarm()
}
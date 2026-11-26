package com.example.mobileship.hardware

import com.example.mobileship.domain.hardware.AlarmSoundManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FakeAlarmSoundManagerTest {

    // Проверяем, что звук изначально выключен
    @Test
    fun AlarmSoundDefaultTest() {
        val soundManager: AlarmSoundManager = FakeAlarmSoundManager()

        assertFalse((soundManager as FakeAlarmSoundManager).isPlaying)
    }

    // Проверяем, что звук можно включить
    @Test
    fun AlarmSoundStartedTest() {
        val soundManager = FakeAlarmSoundManager()

        soundManager.startAlarm()

        assertTrue(soundManager.isPlaying)
        assertEquals(1, soundManager.startCalls)
    }

    // Проверяем, что звук можно выключить
    @Test
    fun AlarmSoundStoppedTest() {
        val soundManager = FakeAlarmSoundManager()

        soundManager.startAlarm()
        soundManager.stopAlarm()

        assertFalse(soundManager.isPlaying)
        assertEquals(1, soundManager.stopCalls)
    }
}
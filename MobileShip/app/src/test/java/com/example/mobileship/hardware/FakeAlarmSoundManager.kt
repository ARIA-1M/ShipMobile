package com.example.mobileship.hardware

import com.example.mobileship.domain.hardware.AlarmSoundManager

class FakeAlarmSoundManager : AlarmSoundManager {

    var isPlaying = false
        private set

    var startCalls = 0
        private set

    var stopCalls = 0
        private set

    override fun startAlarm() {
        isPlaying = true
        startCalls++
    }

    override fun stopAlarm() {
        isPlaying = false
        stopCalls++
    }
}

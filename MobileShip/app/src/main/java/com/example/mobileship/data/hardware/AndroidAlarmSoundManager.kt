package com.example.mobileship.data.hardware

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import com.example.mobileship.domain.hardware.AlarmSoundManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidAlarmSoundManager @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmSoundManager {

    private var mediaPlayer: MediaPlayer? = null

    override fun startAlarm() {
        // Если звук уже воспроизводится, повторно его не запускаем
        if (mediaPlayer?.isPlaying == true) return

        stopAlarm()

        val alarmUri = RingtoneManager.getDefaultUri(
            RingtoneManager.TYPE_ALARM
        ) ?: RingtoneManager.getDefaultUri(
            RingtoneManager.TYPE_NOTIFICATION
        ) ?: return

        try {
            mediaPlayer = MediaPlayer.create(context, alarmUri)?.apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )

                isLooping = true
                start()
            }
        } catch (e: Exception) {
            stopAlarm()
        }
    }

    override fun stopAlarm() {
        mediaPlayer?.let { player ->
            try {
                if (player.isPlaying) {
                    player.stop()
                }
            } catch (_: IllegalStateException) {
                // Проигрыватель уже остановлен или освобожден
            } finally {
                player.release()
            }
        }

        mediaPlayer = null
    }
}
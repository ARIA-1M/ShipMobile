package com.example.mobileship.data.di

import com.example.mobileship.data.hardware.AndroidAlarmSoundManager
import com.example.mobileship.data.hardware.AndroidCameraPermissionChecker
import com.example.mobileship.domain.hardware.AlarmSoundManager
import com.example.mobileship.domain.hardware.CameraPermissionChecker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HardwareModule {

    @Binds
    @Singleton
    abstract fun bindCameraPermissionChecker(
        implementation: AndroidCameraPermissionChecker
    ): CameraPermissionChecker

    @Binds
    @Singleton
    abstract fun bindAlarmSoundManager(
        implementation: AndroidAlarmSoundManager
    ): AlarmSoundManager
}
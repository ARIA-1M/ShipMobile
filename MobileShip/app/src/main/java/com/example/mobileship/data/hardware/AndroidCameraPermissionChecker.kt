package com.example.mobileship.data.hardware

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.mobileship.domain.hardware.CameraPermissionChecker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Реализация для сканирования QR-кодов
 */
class AndroidCameraPermissionChecker @Inject constructor(
    @ApplicationContext private val context: Context
) : CameraPermissionChecker {

    override fun hasPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
}
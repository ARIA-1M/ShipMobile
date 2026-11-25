package com.example.mobileship.hardware

import com.example.mobileship.domain.hardware.CameraPermissionChecker

class FakeCameraPermissionChecker(
    private var permissionGranted: Boolean = false
) : CameraPermissionChecker {

    override fun hasPermission(): Boolean {
        return permissionGranted
    }

    fun setPermissionGranted(granted: Boolean) {
        permissionGranted = granted
    }
}

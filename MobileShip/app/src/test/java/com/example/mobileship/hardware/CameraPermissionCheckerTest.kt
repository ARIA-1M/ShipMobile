package com.example.mobileship.hardware

import com.example.mobileship.domain.hardware.CameraPermissionChecker
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CameraPermissionCheckerTest {

    // Проверяем, что по умолчанию разрешение на камеру отсутствует
    @Test
    fun PermissionDeniedDefaultTest() {
        val checker: CameraPermissionChecker =
            FakeCameraPermissionChecker()

        assertFalse(checker.hasPermission())
    }

    // Проверяем, что разрешение можно предоставить
    @Test
    fun PermissionGrantedTest() {
        val checker = FakeCameraPermissionChecker()

        checker.setPermissionGranted(true)

        assertTrue(checker.hasPermission())
    }

    // Проверяем, что разрешение можно отозвать
    @Test
    fun PermissionDeniedTest() {
        val checker = FakeCameraPermissionChecker(true)

        checker.setPermissionGranted(false)

        assertFalse(checker.hasPermission())
    }
}
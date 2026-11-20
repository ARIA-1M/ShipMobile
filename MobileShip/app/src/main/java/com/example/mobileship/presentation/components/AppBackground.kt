
package com.example.mobileship.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.material3.MaterialTheme

@Composable
fun AppBackground(
    content: @Composable () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            // Большой круг сверху справа
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        colors.primary.copy(alpha = 0.40f),
                        colors.primary.copy(alpha = 0.20f),
                        colors.primary.copy(alpha = 0.01f)
                    ),
                    center = Offset(
                        size.width * 0.98f,
                        size.height * 0.02f
                    ),
                    radius = size.width * 0.85f
                ),
                center = Offset(
                    size.width * 0.98f,
                    size.height * 0.02f
                ),
                radius = size.width * 0.85f
            )

            // Свечение слева снизу
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        colors.primary.copy(alpha = 0.40f),
                        colors.primary.copy(alpha = 0.20f),
                        colors.primary.copy(alpha = 0.01f)
                    ),
                    center = Offset(
                        -size.width * 0.12f,
                        size.height * 0.98f
                    ),
                    radius = size.width * 0.65f
                ),
                center = Offset(
                    -size.width * 0.12f,
                    size.height * 0.98f
                ),
                radius = size.width * 0.65f
            )

            // Небольшое свечение справа снизу
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        colors.secondary.copy(alpha = 0.12f),
                        colors.secondary.copy(alpha = 0f)
                    ),
                    center = Offset(
                        size.width * 1.05f,
                        size.height * 0.85f
                    ),
                    radius = size.width * 0.4f
                ),
                center = Offset(
                    size.width * 1.05f,
                    size.height * 0.85f
                ),
                radius = size.width * 0.4f
            )
        }

        content()
    }
}

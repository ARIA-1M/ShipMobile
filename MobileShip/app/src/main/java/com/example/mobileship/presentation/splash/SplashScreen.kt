package com.example.mobileship.presentation.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mobileship.R
import kotlinx.coroutines.delay

/**
 * Экран загрузки приложения
 * @param onLoadingFinished Колбэк для перехода на следующий экран
 */
@Composable
fun SplashScreen(
    onLoadingFinished: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    val infiniteTransition = rememberInfiniteTransition(label = "shipAnimation")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    var progress by remember { mutableFloatStateOf(0f) }
    var isFinished by remember { mutableStateOf(false) }

    val alphaAnim by animateFloatAsState(
        targetValue = if (isFinished) 0f else 1f,
        animationSpec = tween(durationMillis = 500),
        label = "alpha"
    )

    LaunchedEffect(Unit) {
        val loadingDuration = 1500L
        val steps = 100
        val stepDuration = loadingDuration / steps

        for (i in 1..steps) {
            delay(stepDuration)
            progress = i / 100f
        }
        isFinished = true
        delay(500)
        onLoadingFinished()
    }

    // Анимация точек
    var dotCount by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(300)
            dotCount = (dotCount + 1) % 4
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(alphaAnim),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ship_logo),
                contentDescription = "Ship Logo",
                modifier = Modifier
                    .size(120.dp)
                    .offset(y = offsetY.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "CruiseMind",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = colors.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Загрузка${".".repeat(dotCount)}",
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .width(200.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = colors.primary,
                trackColor = colors.primary.copy(alpha = 0.2f),
            )
        }
    }
}
package com.example.mobileship.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mobileship.presentation.home.HomeUiState

@Composable
fun WelcomeSection(
    uiState: HomeUiState,
    isDarkTheme: Boolean,
    onThemeClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Добро пожаловать,\n${uiState.userRole}!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = colors.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Вы на борту теплохода «Золотое кольцо»",
                style = MaterialTheme.typography.bodySmall,
                color = colors.onSurfaceVariant
            )
        }

        IconButton(
            onClick = onThemeClick,
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = colors.surface,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = if (isDarkTheme) {
                    Icons.Default.LightMode
                } else {
                    Icons.Default.DarkMode
                },
                contentDescription = if (isDarkTheme) {
                    "Включить светлую тему"
                } else {
                    "Включить тёмную тему"
                },
                tint = colors.primary,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}
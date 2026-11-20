package com.example.mobileship.presentation.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mobileship.R
import com.example.mobileship.presentation.components.AppBackground

/**
 * Экран авторизации по QR-коду
 */
@Composable
fun AuthorizationScreen() {
    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Заголовок сверху
            Text(
                text = "АВТОРИЗАЦИЯ",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                textAlign = TextAlign.Center
            )

            // Логотип (Кораблик)
            Image(
                painter = painterResource(id = R.drawable.ship_logo),
                contentDescription = "Ship Logo",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Название приложения
            Text(
                text = "CruiseMind",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Подзаголовок
            Text(
                text = "Ваш персональный ИИ консьерж на борту",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Рамка для QR-кода
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White) // Белый фон для рамки
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Иконка QR-кода внутри
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = "QR Code",
                    modifier = Modifier.size(160.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) // Полупрозрачная иконка
                )

                // Уголки рамки (для красоты)
                // Верхний левый
                Box(modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
                    .size(40.dp)
                    .border(4.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)))
                // Верхний правый
                Box(modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(40.dp)
                    .border(4.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)))
                // Нижний левый
                Box(modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .size(40.dp)
                    .border(4.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)))
                // Нижний правый
                Box(modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .size(40.dp)
                    .border(4.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Текст подсказка
            Text(
                text = "Наведите камеру на QR-код\nв вашей каюте",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}

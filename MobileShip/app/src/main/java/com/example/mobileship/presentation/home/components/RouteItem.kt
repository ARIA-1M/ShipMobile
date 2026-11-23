package com.example.mobileship.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mobileship.domain.model.CityNavigate

@Composable
fun RouteItem(
    city: CityNavigate,
    index: Int,
    isLast: Boolean
) {
    val colors = MaterialTheme.colorScheme

    val isCurrent = city.condition == "current"
    val isPassed = city.condition == "passed"
    val isFirst = index == 0

    val label = when {
        isFirst -> "Отправление"
        isLast -> "Прибытие"
        else -> "$index день"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .width(24.dp)
                .height(34.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(2.dp)
                        .height(26.dp)
                        .background(
                            if (isPassed) {
                                colors.primary
                            } else {
                                colors.outlineVariant
                            }
                        )
                )
            }

            when {
                isCurrent -> {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .background(colors.surface, CircleShape)
                            .border(
                                2.dp,
                                colors.primary,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(colors.primary, CircleShape)
                        )
                    }
                }

                isPassed || isFirst -> {
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(10.dp)
                            .background(colors.primary, CircleShape)
                    )
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(10.dp)
                            .background(
                                colors.outlineVariant,
                                CircleShape
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = city.name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isCurrent || isFirst || isLast) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            color = colors.onSurface,
            modifier = Modifier
                .weight(1f)
                .padding(top = 3.dp)
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = colors.onSurfaceVariant,
            textAlign = TextAlign.End,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

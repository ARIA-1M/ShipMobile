
package com.example.mobileship.presentation.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mobileship.domain.model.Schedule
import com.example.mobileship.presentation.components.AppBackground
import com.example.mobileship.presentation.components.AppCard
import com.example.mobileship.presentation.components.AppHeader

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    AppBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppHeader(title = "Расписание")

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                // Карточка теплохода
                item {
                    AppCard(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 5.dp)
                            .border(
                                width = 1.5.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(24.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "ЗОЛОТОЕ КОЛЬЦО",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = "Команда т/х «Золотое кольцо» приветствует Вас!",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 9.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Капитан: Гогин А. А.",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 9.sp
                                )

                                Text(
                                    text = "Директор круиза: Кривин Н. В.",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }

                // Загрузка
                if (uiState.isLoading && uiState.events.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                // Ошибка
                else if (uiState.error != null && uiState.events.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = uiState.error ?: "Ошибка загрузки",
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center
                            )

                            TextButton(
                                onClick = { viewModel.loadSchedule() }
                            ) {
                                Text("Повторить")
                            }
                        }
                    }
                }

                // Пустое расписание
                else if (uiState.events.isEmpty()) {
                    item {
                        Text(
                            text = "Мероприятий пока нет",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Список мероприятий
                else {
                    items(
                        items = uiState.events,
                        key = { it.id }
                    ) { event ->
                        ScheduleEventItem(event = event)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun ScheduleEventItem(
    event: Schedule
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onSurface
    val secondaryColor = MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = event.startTime.take(5),
            modifier = Modifier
                .width(48.dp)
                .padding(top = 12.dp),
            color = primaryColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(7.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.surface)
                .then(
                    if (event.isNow) {
                        Modifier.border(
                            width = 1.5.dp,
                            color = primaryColor,
                            shape = RoundedCornerShape(14.dp)
                        )
                    } else {
                        Modifier
                    }
                )
                .padding(
                    horizontal = 12.dp,
                    vertical = 10.dp
                )
        ) {
            if (event.isNow) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(9.dp)
                            .height(9.dp)
                            .clip(CircleShape)
                            .background(primaryColor)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Row(
                        modifier = Modifier
                            .background(
                                color = primaryColor,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                )
                        )

                        Text(
                            text = "Идёт сейчас",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))
            }

            Text(
                text = event.title,
                color = textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = event.location,
                color = secondaryColor,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${event.startTime.take(5)} – ${event.endTime.take(5)}",
                color = secondaryColor,
                fontSize = 10.sp
            )
        }
    }
}

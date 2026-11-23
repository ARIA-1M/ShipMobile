
package com.example.mobileship.presentation.aichat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mobileship.presentation.components.AppBackground
import com.example.mobileship.presentation.components.AppHeader

@Composable
fun AiChatScreen(
    onExitClick: () -> Unit,
    viewModel: AiChatViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val colors = MaterialTheme.colorScheme

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            // Верхняя панель
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp)
                    .background(colors.surface)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onExitClick) {
                    Text(
                        text = "<",
                        color = colors.onSurface,
                        fontSize = 28.sp
                    )
                }

                AppHeader("Тревоги")

                Spacer(modifier = Modifier.width(48.dp))
            }

            // Основное содержимое чата
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp, vertical = 14.dp)
            ) {
                // Карточка ИИ-консьержа
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            colors.surface,
                            RoundedCornerShape(20.dp)
                        )
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(colors.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✦",
                            color = colors.onPrimary,
                            fontSize = 25.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "CruiseMind",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.onSurface
                        )

                        Text(
                            text = "ИИ-консьерж онлайн",
                            fontSize = 11.sp,
                            color = colors.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Сообщения
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.messages, key = { it.id }) { message ->
                        MessageBubble(message)
                    }

                    if (state.messages.size == 1) {
                        item {
                            Column(
                                modifier = Modifier.padding(start = 20.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                SuggestionChip("Что сейчас идёт?") {
                                    viewModel.sendMessage("Что сейчас идёт?")
                                }

                                SuggestionChip("Куда сходить?") {
                                    viewModel.sendMessage(
                                        "Куда сходить?"
                                    )
                                }

                                SuggestionChip("Какая сегодня погода?") {
                                    viewModel.sendMessage(
                                        "Какая сегодня погода?"
                                    )
                                }
                            }
                        }
                    }

                    if (state.isLoading) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = colors.primary,
                                    strokeWidth = 2.dp
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = "CruiseMind печатает...",
                                    fontSize = 12.sp,
                                    color = colors.onSurface
                                )
                            }
                        }
                    }

                    state.error?.let { error ->
                        item {
                            Text(
                                text = error,
                                color = colors.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Поле ввода сообщения
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.surface, CircleShape)
                        .padding(
                            start = 16.dp,
                            end = 8.dp,
                            top = 7.dp,
                            bottom = 7.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = state.inputText,
                        onValueChange = viewModel::onInputChange,
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                horizontal = 4.dp,
                                vertical = 10.dp
                            ),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = colors.onSurface,
                            fontSize = 13.sp
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Send
                        ),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                viewModel.sendMessage()
                            }
                        ),
                        decorationBox = { innerTextField ->
                            Box {
                                if (state.inputText.isEmpty()) {
                                    Text(
                                        text = "Спросите что-нибудь...",
                                        color = colors.onSurfaceVariant,
                                        fontSize = 13.sp
                                    )
                                }

                                innerTextField()
                            }
                        }
                    )

                    IconButton(
                        onClick = {
                            viewModel.sendMessage()
                        },
                        enabled = state.inputText.isNotBlank() &&
                                !state.isLoading,
                        modifier = Modifier
                            .size(42.dp)
                            .background(
                                color = colors.primary,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Отправить",
                            tint = colors.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(message: AiChatMessage) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) {
            Arrangement.End
        } else {
            Arrangement.Start
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .clip(
                    RoundedCornerShape(
                        topStart = 18.dp,
                        topEnd = 18.dp,
                        bottomStart = if (message.isFromUser) 18.dp else 4.dp,
                        bottomEnd = if (message.isFromUser) 4.dp else 18.dp
                    )
                )
                .background(
                    if (message.isFromUser) {
                        colors.primary
                    } else {
                        colors.surface
                    }
                )
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Text(
                text = message.text,
                color = if (message.isFromUser) {
                    colors.onPrimary
                } else {
                    colors.onSurface
                },
                fontSize = 12.sp,
                lineHeight = 18.sp
            )

            if (message.time.isNotEmpty()) {
                Text(
                    text = message.time,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 5.dp),
                    fontSize = 9.sp,
                    color = if (message.isFromUser) {
                        colors.onPrimary.copy(alpha = 0.8f)
                    } else {
                        colors.onSurfaceVariant
                    }
                )
            }
        }
    }
}

@Composable
private fun SuggestionChip(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Color(0xFFB9E5D3),
        shape = CircleShape,
        border = BorderStroke(1.dp, Color(0xFF00A66C))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 7.dp
            ),
            color = Color(0xFF009B68),
            fontSize = 11.sp
        )
    }
}

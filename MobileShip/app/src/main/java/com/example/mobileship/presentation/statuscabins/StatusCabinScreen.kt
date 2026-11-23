package com.example.mobileship.presentation.statuscabins

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mobileship.domain.model.CabinStatus
import com.example.mobileship.presentation.components.AppBackground
import com.example.mobileship.presentation.components.AppCard
import com.example.mobileship.presentation.components.AppHeader

private val CleaningColor = Color(0xFF2879A8)
private val DoNotDisturbColor = Color(0xFFC58A16)
private val SosColor = Color(0xFFB52C27)

@Composable
fun StatusCabinScreen(
    onBackClick: () -> Unit,
    viewModel: StatusCabinViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val colors = MaterialTheme.colorScheme

    AppBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AppHeader(title = "Статусы кают")

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 10.dp)
                        .size(48.dp)
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "<",
                        fontSize = 28.sp,
                        color = colors.onSurface
                    )
                }
            }

            StatusCabinContent(
                uiState = uiState,
                onDeckSelected = viewModel::selectDeck
            )
        }
    }
}

@Composable
private fun StatusCabinContent(
    uiState: StatusCabinUiState,
    onDeckSelected: (Int?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Статусы кают",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF203B35)
        )

        Spacer(modifier = Modifier.height(14.dp))

        DeckFilter(
            selectedDeck = uiState.selectedDeck,
            onDeckSelected = onDeckSelected
        )

        Spacer(modifier = Modifier.height(14.dp))

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Загрузка...",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            uiState.cabins.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Нет данных о каютах",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp
                    )
                }
            }

            else -> {
                CabinGrid(
                    cabins = uiState.cabins,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        StatusLegend()
    }
}

@Composable
private fun DeckFilter(
    selectedDeck: Int?,
    onDeckSelected: (Int?) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        DeckFilterButton(
            text = "Все",
            selected = selectedDeck == null,
            modifier = Modifier.weight(1f),
            onClick = { onDeckSelected(null) }
        )

        (1..3).forEach { deck ->
            DeckFilterButton(
                text = "$deck палуба",
                selected = selectedDeck == deck,
                modifier = Modifier.weight(1f),
                onClick = { onDeckSelected(deck) }
            )
        }
    }
}

@Composable
private fun DeckFilterButton(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .height(40.dp)
            .shadow(
                elevation = 4.dp,
                shape = shape
            )
            .background(
                color = if (selected) {
                    Color(0xFF31584B)
                } else {
                    Color(0xFFECE9E5).copy(alpha = 0.85f)
                },
                shape = shape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) Color.White else Color(0xFF31584B),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CabinGrid(
    cabins: List<CabinStatus>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = cabins,
            key = { it.id }
        ) { cabin ->
            CabinStatusCard(cabin = cabin)
        }
    }
}

@Composable
private fun CabinStatusCard(
    cabin: CabinStatus
) {
    val status = cabin.status.trim().lowercase()

    val isSos = status.contains("sos") || status.contains("тревог")

    val isDoNotDisturb = status.contains("не беспокоить") ||
            status.contains("do not disturb") ||
            status.contains("dnd")

    val cardColor = when {
        isSos -> SosColor
        isDoNotDisturb -> DoNotDisturbColor
        else -> CleaningColor
    }

    val borderColors = when {
        isSos -> listOf(
            Color(0xFFE65A50),
            Color(0xFF9E1717),
            Color(0xFFD94B43)
        )

        isDoNotDisturb -> listOf(
            Color(0xFFFFD76A),
            Color(0xFFB8790D),
            Color(0xFFE9B43F)
        )

        else -> listOf(
            Color(0xFF75C9EF),
            Color(0xFF24668E),
            Color(0xFF4B9FC8)
        )
    }

    val numberColor = when {
        isSos -> Color(0xFFB52C27)
        isDoNotDisturb -> Color(0xFF96610C)
        else -> Color(0xFF286D91)
    }

    val shape = RoundedCornerShape(17.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(
                elevation = 15.dp,
                shape = shape,
                ambientColor = cardColor,
                spotColor = cardColor
            )
            .background(
                color = Color(0xFFECE9E5).copy(alpha = 0.72f),
                shape = shape
            )
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(borderColors),
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(width = 105.dp, height = 58.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            cardColor.copy(alpha = 0.38f),
                            cardColor.copy(alpha = 0.18f),
                            cardColor.copy(alpha = 0f)
                        )
                    ),
                    shape = CircleShape
                )
        )

        Text(
            text = cabin.cabinNumber.toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = numberColor
        )
    }
}

@Composable
private fun StatusLegend() {
    AppCard {
        Text(
            text = "Обозначения статусов",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF203B35)
        )

        Spacer(modifier = Modifier.height(14.dp))

        LegendItem(
            color = CleaningColor,
            title = "Уборка",
            description = "Каюта ожидает уборки"
        )

        Spacer(modifier = Modifier.height(12.dp))

        LegendItem(
            color = DoNotDisturbColor,
            title = "Не беспокоить",
            description = "Не заходить в каюту"
        )

        Spacer(modifier = Modifier.height(12.dp))

        LegendItem(
            color = SosColor,
            title = "SOS",
            description = "Активный сигнал тревоги"
        )
    }
}

@Composable
private fun LegendItem(
    color: Color,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(4.dp)
                )
        )

        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF203B35)
            )

            Text(
                text = description,
                fontSize = 12.sp,
                color = Color(0xFF65736D)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    widthDp = 360,
    heightDp = 760
)
@Composable
private fun StatusCabinScreenPreview() {
    val previewCabins = listOf(
        CabinStatus("1", 104, 1, "cleaning"),
        CabinStatus("2", 107, 1, "dnd"),
        CabinStatus("3", 116, 1, "sos"),
        CabinStatus("4", 208, 2, "cleaning"),
        CabinStatus("5", 210, 2, "dnd"),
        CabinStatus("6", 305, 3, "sos"),
        CabinStatus("7", 306, 3, "cleaning"),
        CabinStatus("8", 307, 3, "cleaning"),
        CabinStatus("9", 308, 3, "dnd")
    )

    AppBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppHeader(title = "Статусы кают")

            StatusCabinContent(
                uiState = StatusCabinUiState(
                    cabins = previewCabins,
                    selectedDeck = null,
                    isLoading = false
                ),
                onDeckSelected = {}
            )
        }
    }
}

package com.example.mobileship.presentation.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.mobileship.presentation.alarms.AlarmViewModel
import com.example.mobileship.presentation.components.AppBackground
import com.example.mobileship.presentation.components.AppHeader

@Composable
fun AlarmScreen(
    onBackClick: () -> Unit,
    viewModel: AlarmViewModel = hiltViewModel()
) {
    val cabins by viewModel.cabins.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val colors = MaterialTheme.colorScheme

    AppBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AppHeader(title = "Тревога")

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

            AlarmContent(
                cabins = cabins,
                isLoading = isLoading,
                onAcknowledgeClick = viewModel::acknowledgeAlarms
            )
        }
    }
}

@Composable
private fun AlarmContent(
    cabins: List<CabinStatus>,
    isLoading: Boolean,
    onAcknowledgeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Тревога",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(18.dp))

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            cabins.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Активных тревог нет",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "На данный момент всё спокойно",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            else -> {
                AlarmCabinGrid(
                    cabins = cabins,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onAcknowledgeClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB52C27)
                    )
                ) {
                    Text(
                        text = "Принять сигнал",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun AlarmCabinGrid(
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
            AlarmCabinCard(cabinNumber = cabin.cabinNumber)
        }
    }
}

@Composable
private fun AlarmCabinCard(cabinNumber: Int) {
    val shape = RoundedCornerShape(17.dp)
    val number = cabinNumber.toString()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(
                elevation = 39.dp,
                shape = shape,
                ambientColor = Color(0xFFB52C27),
                spotColor = Color(0xFFB52C27)
            )
            .background(
                color = Color(0xFFECE9E5).copy(alpha = 0.72f),
                shape = shape
            )
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE65A50),
                        Color(0xFF9E1717),
                        Color(0xFFD94B43)
                    )
                ),
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
                            Color(0xFFB52C27).copy(alpha = 0.38f),
                            Color(0xFFB52C27).copy(alpha = 0.18f),
                            Color(0xFFB52C27).copy(alpha = 0f)
                        )
                    ),
                    shape = CircleShape
                )
        )

        Text(
            text = number,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFB52C27)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    widthDp = 360,
    heightDp = 760
)
@Composable
private fun AlarmScreenPreview() {
    val previewCabins = listOf(
        CabinStatus("1", 104, 1, "sos"),
        CabinStatus("2", 107, 1, "sos"),
        CabinStatus("3", 116, 1, "sos"),
        CabinStatus("4", 208, 2, "sos"),
        CabinStatus("5", 210, 2, "sos"),
        CabinStatus("6", 305, 3, "sos")
    )

    AppBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppHeader(title = "Тревога")

            AlarmContent(
                cabins = previewCabins,
                isLoading = false,
                onAcknowledgeClick = {}
            )
        }
    }
}

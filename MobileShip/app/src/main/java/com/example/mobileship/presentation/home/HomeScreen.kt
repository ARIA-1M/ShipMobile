package com.example.mobileship.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mobileship.domain.model.CityNavigate
import com.example.mobileship.presentation.components.AppBackground
import com.example.mobileship.presentation.components.AppHeader
import com.example.mobileship.presentation.navigation.BottomNavigationBar
import com.example.mobileship.presentation.navigation.HomeTab

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    isDarkTheme: Boolean,
    onThemeClick: () -> Unit,
    selectedTab: HomeTab = HomeTab.Home,
    onTabSelected: (HomeTab) -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // Заголовок
            AppHeader(title = "Главная страница")

            // Адаптивное содержимое
            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                val isTablet = maxWidth >= 600.dp

                if (isTablet) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .widthIn(max = 1100.dp)
                            .align(Alignment.TopCenter)
                            .padding(
                                horizontal = 32.dp,
                                vertical = 20.dp
                            ),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Левая колонка
                        Column(
                            modifier = Modifier
                                .weight(0.85f)
                                .fillMaxHeight()
                        ) {
                            WelcomeSection(
                                uiState = uiState,
                                isDarkTheme = isDarkTheme,
                                onThemeClick = onThemeClick
                            )

                            Spacer(modifier = Modifier.height(28.dp))

                            CabinCard(
                                cabinNumber = uiState.cabinNumber
                            )
                        }

                        // Правая колонка
                        RouteCard(
                            route = uiState.route,
                            modifier = Modifier
                                .weight(1.15f)
                                .fillMaxHeight()
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = 14.dp,
                                vertical = 12.dp
                            )
                    ) {
                        WelcomeSection(
                            uiState = uiState,
                            isDarkTheme = isDarkTheme,
                            onThemeClick = onThemeClick
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        CabinCard(
                            cabinNumber = uiState.cabinNumber
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        RouteCard(
                            route = uiState.route,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                    }
                }
            }

            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected
            )
        }
    }
}

@Composable
private fun WelcomeSection(
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

@Composable
private fun CabinCard(
    cabinNumber: String
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        colors.secondaryContainer,
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Bed,
                    contentDescription = null,
                    tint = colors.primary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Ваша каюта",
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.onSurfaceVariant
                )

                Text(
                    text = cabinNumber,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = colors.onSurface
                )
            }
        }
    }
}

@Composable
private fun RouteCard(
    route: List<CityNavigate>,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = null,
                    tint = colors.primary,
                    modifier = Modifier.size(26.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Маршрут круиза",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colors.onSurface,
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    shape = RoundedCornerShape(30.dp),
                    color = colors.secondaryContainer
                ) {
                    Text(
                        text = "${route.size} дней",
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 7.dp
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                itemsIndexed(route) { index, city ->
                    RouteItem(
                        city = city,
                        index = index,
                        isLast = index == route.lastIndex
                    )
                }
            }
        }
    }
}

@Composable
fun RouteItem(
    city: CityNavigate,
    index: Int,
    isLast: Boolean
) {
    val colors = MaterialTheme.colorScheme
    val isCurrent = city.condition == "current"
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
                            if (isFirst || isCurrent) {
                                colors.primary
                            } else {
                                colors.outlineVariant
                            }
                        )
                )
            }

            if (isCurrent) {
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
            } else {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(8.dp)
                        .background(
                            if (isFirst) colors.primary
                            else colors.outlineVariant,
                            CircleShape
                        )
                )
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

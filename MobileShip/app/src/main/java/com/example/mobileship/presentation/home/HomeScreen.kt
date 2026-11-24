package com.example.mobileship.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileship.presentation.components.AppBackground
import com.example.mobileship.presentation.components.AppHeader
import com.example.mobileship.presentation.home.components.CabinCard
import com.example.mobileship.presentation.home.components.RouteCard
import com.example.mobileship.presentation.home.components.WelcomeSection
import com.example.mobileship.presentation.navigation.BottomNavigationBar
import com.example.mobileship.presentation.navigation.HomeTab

/**
 * Главная страница
 */
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    isDarkTheme: Boolean,
    onThemeClick: () -> Unit,
    selectedTab: HomeTab = HomeTab.Home,
    onTabSelected: (HomeTab) -> Unit = {}
) {
    AppBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppHeader(title = "Главная страница")

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
                                cabinNumber = uiState.cabinNumber.toString()
                            )
                        }

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
                            cabinNumber = uiState.cabinNumber.toString()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        if (uiState.isLoading) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary,
                                    strokeWidth = 4.dp,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        } else {
                            RouteCard(
                                route = uiState.route,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
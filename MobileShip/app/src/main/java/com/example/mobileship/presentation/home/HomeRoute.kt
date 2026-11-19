
package com.example.mobileship.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mobileship.presentation.navigation.HomeTab

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    isDarkTheme: Boolean,
    onThemeClick: () -> Unit,
    selectedTab: HomeTab = HomeTab.Home,
    onTabSelected: (HomeTab) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        isDarkTheme = isDarkTheme,
        onThemeClick = onThemeClick,
        selectedTab = selectedTab,
        onTabSelected = onTabSelected
    )
}

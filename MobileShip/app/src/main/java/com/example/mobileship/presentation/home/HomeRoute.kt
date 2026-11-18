package com.example.mobileship.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

/**
 * Обертка для главного экрана которая связывает ViewModel и UI
 */
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    isDarkTheme: Boolean,
    onThemeClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        isDarkTheme = isDarkTheme,
        onThemeClick = onThemeClick
    )
}
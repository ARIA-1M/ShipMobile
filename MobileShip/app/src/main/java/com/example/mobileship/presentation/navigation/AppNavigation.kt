package com.example.mobileship.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.mobileship.presentation.aichat.AiChatScreen
import com.example.mobileship.presentation.authorization.AuthorizationScreen
import com.example.mobileship.presentation.cities.CitiesScreen
import com.example.mobileship.presentation.cities.CitiesViewModel
import com.example.mobileship.presentation.cities.InformationCitiesScreen
import com.example.mobileship.presentation.home.HomeRoute
import com.example.mobileship.presentation.informationcities.InformationCitiesViewModel
import com.example.mobileship.presentation.schedule.ScheduleScreen
import com.example.mobileship.presentation.splash.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onThemeClick: () -> Unit
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val selectedTab = when (currentRoute) {
        Screen.Home -> HomeTab.Home
        Screen.Schedule -> HomeTab.Schedule
        Screen.Chat -> HomeTab.Chat
        Screen.Cities -> HomeTab.Cities
        else -> HomeTab.Home
    }

    val showBottomBar = currentRoute in listOf(
        Screen.Home,
        Screen.Schedule,
        Screen.Cities
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Splash,
            modifier = Modifier.weight(1f)
        ) {
            composable(Screen.Splash) {
                SplashScreen(
                    onLoadingFinished = {
                        navController.navigate("authorization") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Authorization) {
                AuthorizationScreen(
                    onLoginClick = {
                        navController.navigate(Screen.Home) {
                            popUpTo(Screen.Authorization) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Screen.Home) {
                HomeRoute(
                    isDarkTheme = isDarkTheme,
                    onThemeClick = onThemeClick
                )
            }

            composable(Screen.Schedule) {
                ScheduleScreen()
            }

            composable(Screen.Chat) {
                AiChatScreen(
                    onExitClick = {
                        navController.navigate(Screen.Home) {
                            popUpTo(Screen.Home) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Screen.Cities) {
                val viewModel: CitiesViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                CitiesScreen(
                    cities = uiState.cities,
                    onCityClick = { cityId ->
                        navController.navigate("information_cities/$cityId")
                    }
                )
            }

            composable(
                route = "information_cities/{cityId}",
                arguments = listOf(
                    navArgument("cityId") {
                        type = NavType.StringType
                    }
                )
            ) {
                val viewModel: InformationCitiesViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                InformationCitiesScreen(
                    uiState = uiState,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        if (showBottomBar) {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    navigateToTab(navController, tab)
                }
            )
        }
    }
}

private fun navigateToTab(
    navController: NavHostController,
    tab: HomeTab
) {
    val route = when (tab) {
        HomeTab.Home -> Screen.Home
        HomeTab.Schedule -> Screen.Schedule
        HomeTab.Chat -> Screen.Chat
        HomeTab.Cities -> Screen.Cities
    }

    navController.navigate(route) {
        launchSingleTop = true

        popUpTo(Screen.Home) {
            saveState = true
        }

        restoreState = true
    }
}

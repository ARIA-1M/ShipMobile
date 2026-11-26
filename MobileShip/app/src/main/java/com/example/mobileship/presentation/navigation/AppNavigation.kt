package com.example.mobileship.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.mobileship.presentation.alarm.AlarmScreen
import com.example.mobileship.presentation.authorization.AuthorizationScreen
import com.example.mobileship.presentation.cities.CitiesScreen
import com.example.mobileship.presentation.cities.CitiesViewModel
import com.example.mobileship.presentation.cities.InformationCitiesScreen
import com.example.mobileship.presentation.home.HomeRoute
import com.example.mobileship.presentation.informationcities.InformationCitiesViewModel
import com.example.mobileship.presentation.schedule.ScheduleScreen
import com.example.mobileship.presentation.splash.SplashScreen
import com.example.mobileship.presentation.statuscabins.StatusCabinScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onThemeClick: () -> Unit
) {
    var currentCabinNumber by remember {
        mutableIntStateOf(0)
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val isHomeRoute = currentRoute?.startsWith("home/") == true

    val selectedTab = when (currentRoute) {
        Screen.Schedule -> HomeTab.Schedule
        Screen.Chat -> HomeTab.Chat
        Screen.Cities -> HomeTab.Cities
        else -> HomeTab.Home
    }

    val showBottomBar = isHomeRoute || currentRoute in listOf(
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
                        navController.navigate(Screen.Authorization) {
                            popUpTo(Screen.Splash) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Screen.Authorization) {
                AuthorizationScreen(
                    onUserLoginSuccess = { cabinNumber ->
                        currentCabinNumber = cabinNumber

                        navController.navigate(Screen.home(cabinNumber)) {
                            popUpTo(Screen.Authorization) {
                                inclusive = true
                            }
                        }
                    },
                    onAdminLoginSuccess = {
                        navController.navigate(Screen.Alarms) {
                            popUpTo(Screen.Authorization) {
                                inclusive = true
                            }
                        }
                    },
                    onManagerLoginSuccess = {
                        navController.navigate(Screen.StatusCabin) {
                            popUpTo(Screen.Authorization) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(
                route = Screen.Home,
                arguments = listOf(
                    navArgument("cabinNumber") {
                        type = NavType.IntType
                    }
                )
            ) { entry ->
                val cabinNumber = entry.arguments?.getInt("cabinNumber") ?: 0
                currentCabinNumber = cabinNumber

                HomeRoute(
                    cabinNumber = cabinNumber,
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
                        navController.navigate(Screen.home(currentCabinNumber)) {
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

            composable(Screen.Alarms) {
                AlarmScreen(
                    onBackClick = {
                        navController.navigate(Screen.Authorization) {
                            popUpTo(Screen.Alarms) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Screen.StatusCabin) {
                StatusCabinScreen(
                    onBackClick = {
                        navController.navigate(Screen.Authorization) {
                            popUpTo(Screen.StatusCabin) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }

        if (showBottomBar) {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    navigateToTab(
                        navController = navController,
                        tab = tab,
                        cabinNumber = currentCabinNumber
                    )
                }
            )
        }
    }
}

private fun navigateToTab(
    navController: NavHostController,
    tab: HomeTab,
    cabinNumber: Int
) {
    val route = when (tab) {
        HomeTab.Home -> Screen.home(cabinNumber)
        HomeTab.Schedule -> Screen.Schedule
        HomeTab.Chat -> Screen.Chat
        HomeTab.Cities -> Screen.Cities
    }

    navController.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}

package com.example.mobileship

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.mobileship.presentation.navigation.AppNavigation
import com.example.mobileship.ui.theme.MobileShipTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            var isDarkTheme by rememberSaveable {
                mutableStateOf(false)
            }

            val navController = rememberNavController()

            MobileShipTheme(
                darkTheme = isDarkTheme,
                dynamicColor = false
            ) {
                AppNavigation(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    onThemeClick = {
                        isDarkTheme = !isDarkTheme
                    }
                )
            }
        }
    }
}

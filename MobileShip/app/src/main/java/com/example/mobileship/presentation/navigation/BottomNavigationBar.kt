package com.example.mobileship.presentation.navigation

import androidx.compose.material3.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class HomeTab {
    Home, Schedule, Chat, Cities
}

@Composable
fun BottomNavigationBar(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = colors.surface,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .heightIn(min = 64.dp)
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationItem(
                title = "Дом",
                icon = Icons.Default.Home,
                tab = HomeTab.Home,
                selectedTab = selectedTab,
                onClick = onTabSelected
            )

            NavigationItem(
                title = "Расписание",
                icon = Icons.Default.CalendarMonth,
                tab = HomeTab.Schedule,
                selectedTab = selectedTab,
                onClick = onTabSelected
            )

            NavigationItem(
                title = "ИИ-чат",
                icon = Icons.Default.Chat,
                tab = HomeTab.Chat,
                selectedTab = selectedTab,
                onClick = onTabSelected
            )

            NavigationItem(
                title = "Города",
                icon = Icons.Default.Map,
                tab = HomeTab.Cities,
                selectedTab = selectedTab,
                onClick = onTabSelected
            )
        }
    }
}

@Composable
private fun NavigationItem(
    title: String,
    icon: ImageVector,
    tab: HomeTab,
    selectedTab: HomeTab,
    onClick: (HomeTab) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val selected = selectedTab == tab

    Column(
        modifier = Modifier
            .widthIn(min = 64.dp)
            .clickable { onClick(tab) }
            .padding(horizontal = 4.dp, vertical = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .widthIn(min = 48.dp)
                .height(32.dp)
                .background(
                    color = if (selected) {
                        colors.secondaryContainer
                    } else {
                        colors.surface
                    },
                    shape = RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (selected) {
                    colors.primary
                } else {
                    colors.onSurfaceVariant
                },
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = title,
            fontSize = 10.sp,
            color = if (selected) {
                colors.primary
            } else {
                colors.onSurfaceVariant
            },
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }
        )
    }
}

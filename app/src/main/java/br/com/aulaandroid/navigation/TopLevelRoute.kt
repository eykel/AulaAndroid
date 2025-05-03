package br.com.aulaandroid.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelRoute<T : Any>(
    val name: String, val route: T,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
val topLevelRoute = listOf(
    TopLevelRoute("Favorite", Route.FavoriteScreen, Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
    TopLevelRoute("Home", Route.HomeScreen, Icons.Filled.Home, Icons.Outlined.Home),
    TopLevelRoute("Setting", Route.SettingScreen, Icons.Filled.Settings, Icons.Outlined.Settings)
)

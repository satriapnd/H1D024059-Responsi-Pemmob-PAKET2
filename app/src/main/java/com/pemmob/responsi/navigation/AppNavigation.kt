package com.pemmob.responsi.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pemmob.responsi.ui.screens.DetailScreen
import com.pemmob.responsi.ui.screens.HomeScreen
import com.pemmob.responsi.ui.viewmodel.BookViewModel

@Composable
fun AppNavigation(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val navController = rememberNavController()
    
    val sharedViewModel: BookViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = sharedViewModel,
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme,
                onNavigateToDetail = {
                    navController.navigate("detail")
                }
            )
        }
        composable("detail") {
            DetailScreen(
                viewModel = sharedViewModel,
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

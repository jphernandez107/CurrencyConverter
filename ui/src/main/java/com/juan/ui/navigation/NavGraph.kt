package com.juan.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.juan.ui.screen.convert.ConvertScreen
import com.juan.ui.screen.details.DetailsScreen
import com.juan.ui.screen.details.DetailsViewModel
import com.juan.ui.screen.history.HistoryScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val title = when (currentDestination?.route) {
        Screen.Convert.route -> "Conversor de monedas"
        Screen.History.route -> "Historial de conversiones"
        Screen.Details.route -> "Detalles de conversión"
        else -> "GreenRate"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Convert.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = Screen.Convert.route,
            ) {
                ConvertScreen(
                    onHistoryButtonClick = {
                        navController.navigate(Screen.History.route)
                    }
                )
            }
            composable(
                route = Screen.History.route,
                enterTransition = {
                    slideInVertically(
                        animationSpec = tween(500),
                        initialOffsetY = { it },
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        animationSpec = tween(500),
                        targetOffsetX = { -it },
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        animationSpec = tween(500),
                        initialOffsetX = { -it },
                    )
                },
                popExitTransition = {
                    slideOutVertically(
                        animationSpec = tween(500),
                        targetOffsetY = { it },
                    )
                },
            ) {
                HistoryScreen(
                    onItemClick = { id ->
                        navController.navigate(Screen.Details.createRoute(id.toString()))
                    }
                )
            }
            composable(
                route = Screen.Details.route,
                enterTransition = {
                    slideInHorizontally(
                        animationSpec = tween(500),
                        initialOffsetX = { it },
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        animationSpec = tween(500),
                        targetOffsetX = { it },
                    )
                },
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString(DetailsViewModel.CONVERSION_ID_KEY)?.toLongOrNull()
                id?.let {
                    DetailsScreen()
                }
            }
        }
    }
}
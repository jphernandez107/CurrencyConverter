package com.juan.ui.navigation

sealed class Screen(
    val route: String,
) {
    data object Convert : Screen("convert")
    data object History : Screen("history")
    data object Details : Screen("details/{id}") {
        fun createRoute(id: String) = "details/$id"
    }
}
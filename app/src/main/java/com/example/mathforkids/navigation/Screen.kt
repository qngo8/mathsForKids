package com.example.mathforkids.navigation

/**
 * Sealed class for type-safe navigation routes
 * Simple navigation structure suitable for young children
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Menu : Screen("menu")
    object LevelSelection : Screen("level_selection")
    object Game : Screen("game/{gameType}/{level}") {
        fun createRoute(gameType: String, level: Int) = "game/$gameType/$level"
    }
    object Dashboard : Screen("dashboard")
}

package com.example.assignment4_pagination.utils

import androidx.annotation.DrawableRes
import com.example.assignment4_pagination.R

sealed class AppScreen(
    val route: String,
    @DrawableRes val selectedIcon: Int
) {
    object SearchScreen : AppScreen("GitHub Users", selectedIcon = R.drawable.ic_launcher_foreground)
    object UserDetailScreen : AppScreen(
        "User Details",
        selectedIcon = R.drawable.ic_launcher_foreground
    )

    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }
}

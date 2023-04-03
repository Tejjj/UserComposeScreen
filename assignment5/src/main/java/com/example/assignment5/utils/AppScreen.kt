package com.example.assignment5.utils

import androidx.annotation.DrawableRes
import com.example.assignment5.R

sealed class AppScreen(
    val route: String,
    @DrawableRes val selectedIcon: Int
) {
    object ProfileScreen : AppScreen("Profile", selectedIcon = R.drawable.ic_launcher_foreground)
    object UserPostDetailScreen : AppScreen(
        "Post Details",
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

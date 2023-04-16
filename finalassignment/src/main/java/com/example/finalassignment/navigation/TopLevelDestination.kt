package com.example.finalassignment.navigation

import androidx.annotation.DrawableRes
import com.example.finalassignment.R

sealed class TopLevelDestination(
    val title: String,
    val route: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
) {
    object User : TopLevelDestination(
        title = "User",
        route = "user",
        selectedIcon = R.drawable.ic_user_filled,
        unselectedIcon = R.drawable.ic_user_outlined
    )

    object Settings : TopLevelDestination(
        title = "Settings",
        route = "settings",
        selectedIcon = R.drawable.ic_setting_filled,
        unselectedIcon = R.drawable.ic_settings
    )

    object Profile : TopLevelDestination(
        title = "Profile",
        route = "profile",
        selectedIcon = R.drawable.ic_user_filled,
        unselectedIcon = R.drawable.ic_user_outlined
    )

    object PostDetail : TopLevelDestination(
        title = "User Details",
        route = "userDetails",
        selectedIcon = R.drawable.ic_setting_filled,
        unselectedIcon = R.drawable.ic_settings
    )
    object CreatePost : TopLevelDestination(
        title = "Create Post",
        route = "createPost",
        selectedIcon = R.drawable.ic_user_filled,
        unselectedIcon = R.drawable.ic_user_outlined
    )

    /**
     * Use this function to pass arguments to navigation destination
     */
    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

val bottomNavItems = listOf(
    TopLevelDestination.User,
    TopLevelDestination.Settings
)




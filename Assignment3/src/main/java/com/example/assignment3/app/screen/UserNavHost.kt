package com.example.assignment3.app.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.assignment3.app.viewModel.UserViewModel

@Composable
fun UserNavHost(
    viewModel: UserViewModel,
    navController: NavHostController,
    startDestinationScreen: String = AppScreen.MainScreen.route,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationScreen,
        modifier = modifier
    ) {
        composable(route = AppScreen.MainScreen.route) {
            MainScreen {
                viewModel.loadUserList()
                /*navController.navigate(AppScreen.UserListScreen.route) {
                    launchSingleTop = true
                }*/
                navController.popBackStack(route = AppScreen.UserListScreen.route,inclusive = true, saveState = false)
            }
        }

        composable(route = AppScreen.UserListScreen.route) {
            UserListScreen(viewModel,
                addUserEntry = {
                    addUserEntry(viewModel)
                    return@UserListScreen true
                },
                onUserItemClicked = { userId ->
                    navController.navigate(AppScreen.UserDetailScreen.route + "/$userId")
                })
        }

        composable(
            route = AppScreen.UserDetailScreen.route + "/{userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.IntType;
                    defaultValue = 1
                })
        ) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getInt("userId",1)
            UserDetailsScreen(viewModel, userId,
                onNavigateUp = { navController.popBackStack() })
        }
    }
}

fun addUserEntry(viewModel: UserViewModel) {
    viewModel.addUser()
}

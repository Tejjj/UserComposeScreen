package com.example.assignment3.app.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assignment3.app.viewModel.UserViewModel

@Composable
fun UserNavHost(
    viewModel: UserViewModel,
    navController: NavHostController = rememberNavController(),
    startDestinationScreen: String = AppScreen.MainScreen.route,
    modifier: Modifier = Modifier
) {
    var context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestinationScreen,
        modifier = modifier
    ) {
        composable(route = AppScreen.MainScreen.route) {
            MainScreen {
                viewModel.loadUserList()
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
                })
        ) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getInt("userId")
            UserDetailsScreen(viewModel, userId,
                onNavigateUp = { navController.popBackStack() })
        }
    }
}

fun addUserEntry(viewModel: UserViewModel) {
    viewModel.addUser()
}

package com.example.assignment3.app.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.assignment3.app.viewModel.UserViewModel

@Composable
fun UserNavHost(
    viewModel: UserViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.MainScreen.route,
        modifier = modifier
    ) {
        composable(route = AppScreen.MainScreen.route) {
            MainScreen {
                viewModel.loadUserList()
                navController.navigate(AppScreen.UserListScreen.route)
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
                    type = NavType.IntType
                })
        ) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getInt("item")
            UserDetailsScreen(viewModel, userId, navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}

fun addUserEntry(viewModel: UserViewModel) {
    viewModel.addUser()
}

package com.example.assignment3.app.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assignment3.app.viewModel.UserViewModel

@Composable
fun UserNavHost(
    viewModel: UserViewModel = hiltViewModel(),
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
            MainScreen(viewModel, navController)
        }

        composable(route = AppScreen.UserListScreen.route) {
            UserListScreen(viewModel, navController)/*addUserEntry = {
                //addUserEntry(viewModel)
                return@UserListScreen true
            }, onUserItemClicked = { userId ->
                navController.navigate(AppScreen.UserDetailScreen.route + "/$userId")
            })*/
        }

        composable(
            route = AppScreen.UserDetailScreen.route + "/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.IntType;
            })
        ) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getInt("userId")
            if (userId == null) {
                Toast.makeText(
                    context,
                    "User information is not present for this user, Please try with correct User id.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                UserDetailsScreen(
                    viewModel,
                    userId,
                    onNavigateUp = { navController.popBackStack() })
            }
        }
    }
}

fun addUserEntry(viewModel: UserViewModel) {
    viewModel.addUser()
}

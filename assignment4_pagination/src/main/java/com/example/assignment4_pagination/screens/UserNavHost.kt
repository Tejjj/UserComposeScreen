package com.example.assignment4_pagination.screens

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
import com.example.assignment4_pagination.screens.viewModel.SearchViewModel
import com.example.assignment4_pagination.screens.viewModel.UserDetailsViewModel
import com.example.assignment4_pagination.utils.AppScreen

@Composable
fun UserNavHost(
    viewModel: SearchViewModel = hiltViewModel(),
    userViewModel: UserDetailsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    var context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = AppScreen.SearchScreen.route,
        modifier = modifier
    ) {
        composable(route = AppScreen.SearchScreen.route) {
            SearchScreen(modifier, viewModel, navController)
        }

        composable(
            route = AppScreen.UserDetailScreen.route + "/{userName}",
            arguments = listOf(navArgument("userName") {
                type = NavType.StringType;
            })
        ) { navBackStackEntry ->
            val userName = navBackStackEntry.arguments?.getString("userName")
            if (userName == null) {
                Toast.makeText(
                    context,
                    "Github entries are not present for this user, Please try with correct User name.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                UserDetailsScreen(
                    userViewModel,
                    userName,
                    navController,
                    onNavigateUp = { navController.popBackStack() })
            }
        }
    }
}

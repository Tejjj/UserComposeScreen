package com.example.assignment5.screens

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
import com.example.assignment5.screens.viewModel.PostDetailViewModel
import com.example.assignment5.screens.viewModel.UserViewModel
import com.example.assignment5.utils.AppScreen

@Composable
fun UserNavHost(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel(),
    postDetailViewModel: PostDetailViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = AppScreen.ProfileScreen.route,
        modifier = modifier
    ) {
        composable(route = AppScreen.ProfileScreen.route) {
            UserProfileScreen(modifier, viewModel, navController)
        }

        composable(route = AppScreen.UserPostDetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("id")
            PostDetailsScreen(modifier, postDetailViewModel, navController)
        }
    }
}
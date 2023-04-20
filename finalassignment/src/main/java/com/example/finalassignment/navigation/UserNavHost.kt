package com.example.finalassignment.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalassignment.screens.CreatePostScreen
import com.example.finalassignment.screens.PostDetailsScreen
import com.example.finalassignment.screens.ProfileScreen
import com.example.finalassignment.screens.SettingsScreen
import com.example.finalassignment.screens.UserListScreen
import com.example.finalassignment.screens.viewModel.CreatePostViewModel
import com.example.finalassignment.screens.viewModel.PostDetailsViewModel
import com.example.finalassignment.screens.viewModel.ProfileViewModel
import com.example.finalassignment.screens.viewModel.SettingsViewModel
import com.example.finalassignment.screens.viewModel.UsersViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserNavHost(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {

    val context = LocalContext.current

    val usersViewModel: UsersViewModel = hiltViewModel()

    val profileViewModel: ProfileViewModel = hiltViewModel()

    val postDetailsViewModel: PostDetailsViewModel = hiltViewModel()

    val createPostViewModel: CreatePostViewModel = hiltViewModel()

    val settingsViewModel: SettingsViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = TopLevelDestination.User.route,
        modifier = modifier.background(MaterialTheme.colorScheme.primary)
    ) {
        composable(route = TopLevelDestination.User.route) {
            UserListScreen(usersViewModel, navController)
        }

        composable(
            route = TopLevelDestination.Profile.route + "/{userId}",
            arguments = listOf(navArgument(name = "userId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")

            userId?.let { ProfileScreen(userId, navController) }
        }

        composable(
            route = TopLevelDestination.PostDetail.route + "/{userId}" + "/{postId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                },
                navArgument(name = "postId") {
                    type = NavType.StringType
                },
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            val postId = backStackEntry.arguments?.getString("postId")

            if (userId.isNullOrEmpty() || postId.isNullOrEmpty()) {
                Toast.makeText(
                    context,
                    "Post details not present for this Post",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                PostDetailsScreen(postDetailsViewModel, userId, postId, navController)
            }
        }

        composable(
            route = TopLevelDestination.CreatePost.route + "/{userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                },
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            if (userId.isNullOrEmpty()) {
                //Toast.makeText(context, "Invalid user id", Toast.LENGTH_SHORT).show()
            } else {
                CreatePostScreen(createPostViewModel, userId, navController)
            }
        }


        composable(route = TopLevelDestination.Settings.route) {
            SettingsScreen(navController)
        }
    }
}


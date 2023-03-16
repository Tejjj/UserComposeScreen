@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.usercomposescreen.app.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.usercomposescreen.app.viewModel.UserViewModel

sealed class AppScreen(val route: String) {
    object UsersScreen : AppScreen("Users")
    object FavouriteScreen : AppScreen("Favourites")
    object TrendingScreen : AppScreen("Trending")
}

@Composable
fun UserAppBar() {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .border(BorderStroke(2.dp, Color.Black))
                    .fillMaxWidth()
                    .padding(8.dp),
                text = AppScreen.UsersScreen.route,
                style = MaterialTheme.typography.headlineLarge
            )
        },
        modifier = Modifier
            .padding(end = 8.dp)
            .fillMaxWidth()
            .height(64.dp)
            .paddingFromBaseline(top = 8.dp)
            .background(Color.White, shape = RectangleShape),
    )
}

@ExperimentalMaterial3Api
@Composable
fun UserComposeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val userViewModel = UserViewModel()

    Scaffold(
        topBar = {
            UserAppBar()
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.UsersScreen.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = AppScreen.UsersScreen.route) {
                UserScreen(userViewModel) { user ->
                    userViewModel.removeUser(user = user)
                }
            }
        }
    }

}
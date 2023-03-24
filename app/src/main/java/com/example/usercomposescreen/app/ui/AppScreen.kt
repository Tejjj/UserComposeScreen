package com.example.usercomposescreen.app.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAppBar() {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineLarge,
                text = AppScreen.UsersScreen.route
            )
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AccountBox, contentDescription = null)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant))
    )
}

@ExperimentalMaterial3Api
@Composable
fun UserComposeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val userViewModel: UserViewModel = hiltViewModel()

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
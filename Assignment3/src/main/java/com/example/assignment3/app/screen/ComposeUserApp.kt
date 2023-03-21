package com.example.assignment3.app.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assignment3.app.viewModel.UserViewModel

/*@Preview
@Composable
fun APpPreview() {
    ComposeUserApp()
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeUserApp(
    viewModel: UserViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    UserNavHost(viewModel, navController)
}


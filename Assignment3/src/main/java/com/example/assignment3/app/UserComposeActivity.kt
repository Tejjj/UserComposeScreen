package com.example.assignment3.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.assignment3.app.screen.AppScreen
import com.example.assignment3.app.screen.UserNavHost
import com.example.assignment3.app.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserComposeActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        lifecycleScope.launch {
            userViewModel.userAvailable.collect { isDataAvailable ->

                setContent {
                    UserNavHost(
                        userViewModel,
                        startDestinationScreen = if (isDataAvailable) {
                            AppScreen.UserListScreen.route
                        } else {
                            AppScreen.MainScreen.route
                        }
                    )
                }
            }
        }
    }
}
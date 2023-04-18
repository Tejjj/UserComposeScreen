package com.example.finalassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.finalassignment.navigation.NavApp
import com.example.finalassignment.navigation.UserNavHost
import com.example.finalassignment.screens.viewModel.SettingsViewModel
import com.example.finalassignment.ui.theme.UserComposeScreenTheme
import com.example.finalassignment.utils.updateResources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
                NavApp()
        }

        lifecycleScope.launch {
            settingsViewModel.language.collect { language ->
                updateResources(this@MainActivity, language)
            }
        }
    }
}
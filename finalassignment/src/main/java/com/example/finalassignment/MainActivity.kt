package com.example.finalassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.finalassignment.navigation.NavApp
import com.example.finalassignment.navigation.UserNavHost
import com.example.finalassignment.ui.theme.UserComposeScreenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           // UserComposeScreenTheme {
                NavApp()
            //}
        }
    }
}
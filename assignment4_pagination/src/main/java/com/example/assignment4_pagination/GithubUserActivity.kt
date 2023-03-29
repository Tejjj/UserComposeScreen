package com.example.assignment4_pagination

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment4_pagination.screens.UserNavHost
import com.example.assignment4_pagination.ui.theme.UserComposeScreenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GithubUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserNavHost()
        }
    }
}
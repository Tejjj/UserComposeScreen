package com.example.assignment3.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.assignment3.app.screen.ComposeUserApp
import com.example.assignment3.app.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userViewModel:UserViewModel by viewModels()

        setContent {
            ComposeUserApp(userViewModel)
        }
    }
}
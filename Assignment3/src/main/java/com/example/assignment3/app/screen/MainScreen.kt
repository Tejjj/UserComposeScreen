package com.example.assignment3.app.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.assignment3.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(loadUser: () -> Unit) {

    Scaffold(
        topBar = {
            UserAppBar(
                title = AppScreen.UserDirectory.route,
                canNavigateBack = false
            )
        }
    ) { innerPadding ->
        LoadButton(Modifier.padding(innerPadding)) {
            loadUser()
        }

    }
}

@Composable
fun LoadButton(modifier: Modifier = Modifier, loadUser: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { loadUser() }) {
            Text(
                text = stringResource(id = R.string.load_user),
                fontSize = 38.sp,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

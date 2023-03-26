package com.example.assignment3.app.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.assignment3.R
import com.example.assignment3.app.viewModel.UserViewModel
import com.example.assignment3.repository.User
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    viewModel: UserViewModel,
    userId: Int,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true
) {
    val user by remember { viewModel.userData }.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUser(userId)
    }

    Scaffold(
        topBar = {
            UserAppBar(
                title = AppScreen.UserDirectory.route,
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(id = R.string.details_welcome_msg),
                style = MaterialTheme.typography.titleLarge, fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(Color.White)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "UserId : ${user?.userId ?: "UserId"}",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Username : ${user?.name ?: "UserName"}",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "fullName : ${user?.fullname ?: "fullName"}",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "email : ${user?.email ?: "email"}",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}


package com.example.assignment4_pagination.screens

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.assignment4_pagination.R
import com.example.assignment4_pagination.model.UserRepoDetails
import com.example.assignment4_pagination.screens.viewModel.UserDetailsViewModel
import com.example.assignment4_pagination.ui.components.VerticalSpacer
import com.example.assignment4_pagination.utils.AppScreen
import com.example.assignment4_pagination.utils.DateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    userViewModel: UserDetailsViewModel,
    userName: String,
    navController: NavHostController,
    onNavigateUp: () -> Unit
) {

    var userRepoInfo = remember { userViewModel.userData }.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.getUserRepoDetails(userName)
    }

    Scaffold(topBar = {
        UserAppBar(
            title = AppScreen.SearchScreen.route, canNavigateBack = true, navigateUp = onNavigateUp
        )
    }) { innerPadding ->
        userRepoInfo.value?.let { userDetails ->
            Card(
                modifier = Modifier.padding(innerPadding),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
                    ProfileUI(Modifier.align(Alignment.CenterHorizontally), userDetails)

                    VerticalSpacer(size = 24)

                    UserRepoDetail(userDetails)

                    VerticalSpacer(24)

                    userDetails.bioGraphy?.let { BioGraphyUI(userDetails.bioGraphy) }

                    VerticalSpacer(40)

                    ProfileCreatedInfoUI(Modifier.align(Alignment.CenterHorizontally),userDetails.createdAt)
                }

            }
        }
    }
}

@Composable
fun ProfileCreatedInfoUI(modifier: Modifier, createdAt: String) {
    var time = createdAt

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        time = DateFormatter.convertTimeStampToCalendarFormat(createdAt)
    }

    Text(modifier = modifier, text = "Created on $time.",
        fontSize = 18.sp,
        style = MaterialTheme.typography.titleSmall,
        textAlign = TextAlign.Center)
}

@Composable
fun BioGraphyUI(bioGraphy: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.bio),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Start,
        )

        Text(
            text = bioGraphy,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }

}

@Composable
fun UserRepoDetail(userDetails: UserRepoDetails) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        UserRepoInfo(userDetails.followers, stringResource(id = R.string.followers))

        Divider(modifier = Modifier
            .height(64.dp)
            .width(4.dp),
            color = Color.Black)

        UserRepoInfo(userDetails.repositories, stringResource(id = R.string.repository))

        Divider(modifier = Modifier
            .height(64.dp)
            .width(4.dp),
            color = Color.Black)

        UserRepoInfo(userDetails.following, stringResource(id = R.string.following))

    }
}

@Composable
fun UserRepoInfo(userInfo: Int, title: String) {
    Column(
        modifier = Modifier.padding(PaddingValues(horizontal = 4.dp)),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = userInfo.toString(),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Start,
            fontSize = 48.sp
        )

        VerticalSpacer(size = 8)

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            fontSize = 16.sp
        )

    }
}


@Composable
fun ProfileUI(modifier: Modifier, userDetails: UserRepoDetails) {
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileImage(
                modifier = Modifier, profileImage = userDetails.profileImageUrl, 200
            )

            VerticalSpacer(size = 8)

            userDetails.fullName?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Start,
                )
            }

            VerticalSpacer(size = 8)

            Text(
                text = userDetails.userName,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
        }
    }

}

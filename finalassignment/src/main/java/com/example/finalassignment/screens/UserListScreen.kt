package com.example.finalassignment.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.finalassignment.R
import com.example.finalassignment.navigation.TopLevelDestination
import com.example.finalassignment.screens.viewModel.UsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UsersViewModel,
    navController: NavHostController
) {

    val profileList = remember {
        viewModel.userProfileList
    }.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .border(BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant)),
                title = {
                    Text(
                        text = TopLevelDestination.User.title,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = TopLevelDestination.User.title,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                },
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        profileList?.let {
            UserListView(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
                users = profileList,
                onUserClick = { profile ->
                    navController.navigate(TopLevelDestination.Profile.withArgs(profile.userId))
                })
        }

        if (profileList == null || profileList.itemCount == 0) {
            Text(
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                text = stringResource(R.string.no_posts)
            )
        }

    }
}

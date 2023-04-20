package com.example.finalassignment.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.finalassignment.R
import com.example.finalassignment.model.ProfileResponse
import com.example.finalassignment.navigation.TopLevelDestination
import com.example.finalassignment.screens.viewModel.UsersViewModel
import com.example.finalassignment.ui.components.LoadingProgressBar
import com.example.finalassignment.utils.shimmerBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UsersViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current

    var showShimmer by remember { mutableStateOf(true) }

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
                text = stringResource(R.string.user_profiles_not_found_please_try_after_sometime)
            )
        }

        showShimmer = profileList.itemCount <= 0

        profileList.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    if(showShimmer){
                        Box(modifier = Modifier.background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer))
                            .fillMaxSize()) {
                        }
                    }

                    //LoadingProgressBar()
                }

                loadState.append is LoadState.Loading -> {
                    if(showShimmer){
                        Box(modifier = Modifier.background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer))
                            .fillMaxSize()) {
                        }
                    }
                    //LoadingProgressBar()
                }

                loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && this.itemCount < 1 -> {
                    Toast.makeText(context, stringResource(id = R.string.end_of_pagination), Toast.LENGTH_SHORT).show()
                }

                loadState.refresh is LoadState.Error -> {
                    Toast.makeText(context, "Error has occured.. retry", Toast.LENGTH_SHORT).show()
                }

                loadState.append is LoadState.Error -> {
                    Toast.makeText(context, "Error has occured.. retry after sometime", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
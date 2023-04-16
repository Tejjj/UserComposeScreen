package com.example.finalassignment.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.finalassignment.R
import com.example.finalassignment.model.UserPostsResponse
import com.example.finalassignment.navigation.TopLevelDestination
import com.example.finalassignment.screens.viewModel.ProfileViewModel
import com.example.finalassignment.ui.components.LoadingProgressBar
import com.example.finalassignment.ui.components.RoundedCornerImage
import com.example.finalassignment.utils.UserUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    userId: String,
    navController: NavHostController,
) {
    val userProfile = remember { viewModel.userProfile }.collectAsState()

    val userPostsUiState = remember { viewModel.userPostState }.collectAsState()

    var userPosts = remember {
        mutableStateOf<List<UserPostsResponse>>(emptyList())
    }

    LaunchedEffect(Unit) {
        viewModel.getUserProfile(userId)
        viewModel.getUserPosts(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .border(BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant)),

                title = {
                    Text(
                        text = TopLevelDestination.Profile.title,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button),
                            modifier = Modifier.size(64.dp)
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            Button(
                onClick = { navController.navigate(TopLevelDestination.CreatePost.withArgs(userId)) },
                // Uses ButtonDefaults.ContentPadding by default
                contentPadding = PaddingValues(8.dp),
            ) {
                // Inner content including an icon and a text label
                Icon(
                    Icons.Filled.AddCircle,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(size = 64.dp)
                )
            }

        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->

        LazyVerticalGrid(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            userProfile.value?.let {
                val profile = userProfile.value
                item(span = { GridItemSpan(2) }) {
                    UserProfileUI(userDetails = profile)
                }
            }

            userPosts?.let {
                items(userPosts.value, span = { GridItemSpan(1) }) { userPost ->
                    UserPostUI(userPostItem = userPost) { userPostId ->
                        navController.navigate(
                            TopLevelDestination.PostDetail.withArgs(
                                userPost.userId,
                                userPostId
                            )
                        )
                    }
                }
            }
        }

        val uiState = userPostsUiState.value
        when (uiState) {
            UserUIState.Empty -> {

            }

            is UserUIState.Failure -> {

            }

            UserUIState.Loading -> {
                LoadingProgressBar()
            }

            is UserUIState.Success -> {
                userPosts.value = uiState.userPosts
            }
        }
    }
}

@Composable
fun UserPostUI(userPostItem: UserPostsResponse?, onPostClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                userPostItem?.let {
                    onPostClicked(userPostItem.postId)
                }
            },
        verticalArrangement = Arrangement.SpaceBetween,
        Alignment.CenterHorizontally,
    ) {
        RoundedCornerImage(
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(BorderStroke(1.dp, Color.Gray)), profileImage = userPostItem?.postImageUrl
        )
    }
}

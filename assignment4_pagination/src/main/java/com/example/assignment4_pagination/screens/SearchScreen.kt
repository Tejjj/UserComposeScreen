package com.example.assignment4_pagination.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.assignment4_pagination.R
import com.example.assignment4_pagination.model.UserRepository
import com.example.assignment4_pagination.screens.viewModel.SearchViewModel
import com.example.assignment4_pagination.ui.components.LoadingProgressBar
import com.example.assignment4_pagination.ui.components.RetryItem
import com.example.assignment4_pagination.ui.components.VerticalSpacer
import com.example.assignment4_pagination.utils.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current

    val repositories = remember { searchViewModel.userSearchResults }.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            UserAppBar(title = AppScreen.SearchScreen.route, canNavigateBack = false)
        }, containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchBar(modifier, onSearch = { searchQuery ->
                if (!searchQuery.isEmpty()) {
                    repositories.refresh()
                    searchViewModel.setSearch(searchQuery)
                } else {
                    Toast.makeText(context, "Please enter username to search", Toast.LENGTH_SHORT)
                        .show()
                }
            }) {
                repositories.refresh()
                searchViewModel.setSearch("")
            }

            SearchRepositoryResultUI(
                modifier = Modifier.fillMaxSize(), repositories = repositories
            ) { userName ->
                navController.navigate(AppScreen.UserDetailScreen.route + "/$userName")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier, onSearch: (String) -> Unit, onClearedUserSearch: () -> Unit
) {
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current

    val keyBoardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier.padding(PaddingValues(horizontal = 8.dp, vertical = 16.dp))) {
        TextField(
            modifier = modifier
                .padding(PaddingValues(8.dp))
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
            value = searchQuery,
            onValueChange = { searchQuery = it },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = {
                    searchQuery = ""
                    onClearedUserSearch()
                }) {
                    Icon(
                        Icons.Default.Clear, contentDescription = "Clear search"
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
            placeholder = {
                Text(stringResource(R.string.placeholder_search))
            },
            singleLine = true,
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(searchQuery)
                keyBoardController?.hide()
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Search
            )
        )

        VerticalSpacer(size = 10)

        Button(
            onClick = {
                onSearch(searchQuery)
                keyBoardController?.hide()
                focusManager.clearFocus()
            },
            modifier = Modifier
                .padding(PaddingValues(horizontal = 16.dp, vertical = 8.dp))
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Text(text = stringResource(id = R.string.search_users))
        }
    }
}

@Composable
fun SearchRepositoryResultUI(
    modifier: Modifier = Modifier,
    repositories: LazyPagingItems<UserRepository>,
    onUserClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(repositories) { repository ->
            repository?.let {
                RepositoryItem(
                    modifier = Modifier.fillMaxWidth(), userRepository = it
                ) { userName ->
                    onUserClicked(userName)
                }
            }
            repository ?: RetryItem(modifier = Modifier.fillMaxSize(),
                errorMsg = stringResource(id = R.string.no_user),
                onRetryClick = { })
        }
    }

    repositories.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                LoadingProgressBar(modifier = Modifier.fillMaxSize())
            }

            loadState.append is LoadState.Loading -> {
                LoadingProgressBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && this.itemCount < 1 -> {
                RetryItem(modifier = Modifier.fillMaxSize(),
                    errorMsg = stringResource(id = R.string.end_of_pagination),
                    onRetryClick = { retry() })
            }

            loadState.refresh is LoadState.Error -> {
                RetryItem(modifier = Modifier.fillMaxSize(),
                    errorMsg = "Error has occured.. retry",
                    onRetryClick = { retry() })
            }

            loadState.append is LoadState.Error -> {
                RetryItem(modifier = Modifier.fillMaxSize(),
                    errorMsg = stringResource(id = R.string.retry),
                    onRetryClick = { retry() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryItem(
    modifier: Modifier = Modifier, userRepository: UserRepository, onUserClicked: (String) -> Unit
) {
    ElevatedCard(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .background(MaterialTheme.colorScheme.background), onClick = {
        onUserClicked(userRepository.userName)
    }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            ProfileImage(modifier = Modifier.weight(0.4f), userRepository.profileImage, 88)

            ProfileDetail(modifier = Modifier.weight(0.6f), userRepository)
        }
    }
}

@Composable
fun ProfileImage(modifier: Modifier, profileImage: String?, size: Int) {
    AsyncImage(
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape),
        model = ImageRequest.Builder(LocalContext.current).data(profileImage).crossfade(true)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(com.google.android.material.R.drawable.mtrl_ic_error).build(),
        contentDescription = null,
    )
}

@Composable
fun ProfileDetail(modifier: Modifier, userRepository: UserRepository) {
    Column(
        modifier = modifier.padding(PaddingValues(8.dp)),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = userRepository.userName, style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Start,
        )

        VerticalSpacer(size = 4)

        Text(
            modifier = Modifier.align(Alignment.Start),
            text = userRepository.profileUrl.toString(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start
        )
    }
}



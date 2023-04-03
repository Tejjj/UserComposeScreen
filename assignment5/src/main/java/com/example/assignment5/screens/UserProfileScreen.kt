package com.example.assignment5.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.assignment5.R
import com.example.assignment5.model.ProfileResponse
import com.example.assignment5.model.UserPostsResponse
import com.example.assignment5.screens.viewModel.UserViewModel
import com.example.assignment5.ui.components.HorizontalSpacer
import com.example.assignment5.ui.components.LoadingProgressBar
import com.example.assignment5.ui.components.ProfileImage
import com.example.assignment5.ui.components.RetryItem
import com.example.assignment5.ui.components.VerticalSpacer
import com.example.assignment5.utils.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    modifier: Modifier, userViewModel: UserViewModel, navController: NavHostController
) {

    val userProfile = remember { userViewModel.userProfile }.collectAsState()

    val userPosts = remember { userViewModel.userPosts }.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(modifier = modifier
                .fillMaxWidth()
                .border(BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant)), title = {
                Text(
                    text = AppScreen.ProfileScreen.route,
                    style = MaterialTheme.typography.headlineLarge
                )
            }, navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = AppScreen.ProfileScreen.route,
                        modifier = Modifier.size(64.dp)
                    )
                }
            })
        }, containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        UserPostGridItems(
            Modifier.padding(innerPadding), userProfile = userProfile?.value, userPosts = userPosts
        ) { userPostId ->
            navController.navigate(AppScreen.UserPostDetailScreen.withArgs(userPostId))
        }
    }
}

@Composable
fun UserPostGridItems(
    modifier: Modifier = Modifier,
    userProfile: ProfileResponse?,
    userPosts: LazyPagingItems<UserPostsResponse>,
    userPostClicked: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item(span = { GridItemSpan(3) }) {
            UserProfileUI(userDetails = userProfile)
        }

        loadUserPosts(userPosts) { userPostId ->
            userPostClicked(userPostId)
        }
    }

    userPosts.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                LoadingProgressBar()
            }

            loadState.append is LoadState.Loading -> {
                LoadingProgressBar()
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

fun LazyGridScope.loadUserPosts(
    userPosts: LazyPagingItems<UserPostsResponse>,
    onPostClicked: (String) -> Unit
) {
    pagingItems(userPosts) { userPostItem ->
        UserPostUI(userPostItem) { postId ->
            onPostClicked(postId)
        }
    }
}


inline fun <T : Any> LazyGridScope.pagingItems(
    items: LazyPagingItems<T>,
    noinline key: ((item: T) -> Any)? = null,
    noinline span: (LazyGridItemSpanScope.(item: T?) -> GridItemSpan)? = null,
    noinline contentType: (item: T?) -> Any? = { null },
    crossinline itemContent: @Composable LazyGridItemScope.(item: T?) -> Unit
) = items(count = items.itemCount,
    key = if (key != null) { index: Int -> items[index]?.let(key) ?: index } else null,
    span = if (span != null) {
        { span(items[it]) }
    } else null,
    contentType = { index: Int -> contentType(items[index]) }) {
    itemContent(items[it])
}

@Composable
fun UserProfileUI(modifier: Modifier = Modifier, userDetails: ProfileResponse?) {
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.Start
        ) {
            ProfileUI(userDetails)

            ProfileDetails(userDetails)

            userDetails?.subscription?.plan?.let {
                SubscriptionPlanUI(userDetails)
            }

            Divider(
                modifier = Modifier
                    .padding(PaddingValues(vertical = 8.dp))
                    .height(3.dp)
                    .fillMaxWidth(),
                color = Color.Black
            )
        }
    }
}


@Composable
fun ProfileUI(userDetails: ProfileResponse?) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImage(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            profileImage = userDetails?.profileImageUrl
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            TextUI(
                textStyle = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                textSize = 28,
                value = userDetails?.username ?: "User Name"
            )

            CustomTextWithIconUI(
                imageVector = Icons.Default.Build,
                title = userDetails?.employment?.title ?: "Employment Title",
                textSize = 12
            )

            CustomTextWithIconUI(
                imageVector = Icons.Default.Email,
                title = userDetails?.email ?: "Email-id",
                textSize = 12
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}


@Composable
fun TextUI(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    textStyle: TextStyle,
    textSize: Int,
    value: String,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        modifier = modifier,
        color = color,
        text = value,
        style = textStyle,
        textAlign = TextAlign.Start,
        fontSize = textSize.sp,
        fontWeight = fontWeight
    )
}


@Composable
fun CustomTextWithIconUI(imageVector: ImageVector?, title: String, textSize: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        imageVector?.let { Icon(imageVector = imageVector, contentDescription = "Work") }
        Spacer(modifier = Modifier.width(3.dp))
        TextUI(
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            textSize = textSize,
            value = title
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetails(userDetails: ProfileResponse?) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(4.dp)
            .fillMaxWidth(),
        onClick = {},
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, Color.Black),
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var streetAddress = buildString {
                append(userDetails?.address?.streetName ?: "")
                append(" , ")
                append(userDetails?.address?.streetAddress)
            }

            DetailComposableUI(
                Modifier.align(Alignment.Start),
                imageVector = Icons.Default.Place,
                title = "Street Name | Street Address",
                details = streetAddress
            )

            VerticalSpacer(size = 4)

            var city = buildString {
                append(userDetails?.address?.city ?: "")
                append(" , ")
                append(userDetails?.address?.state)
            }

            DetailComposableUI(
                Modifier.align(Alignment.Start),
                imageVector = null,
                title = "City | State ",
                details = city ?: "UnAvailable"
            )

            VerticalSpacer(size = 4)

            var country = buildString {
                append(userDetails?.address?.country ?: "")
                append(" , ")
                append(userDetails?.address?.zipCode)
            }

            DetailComposableUI(
                Modifier.align(Alignment.Start),
                imageVector = null,
                title = "Country",
                details = country ?: "UnAvailable"
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun DetailComposableUI(
    modifier: Modifier = Modifier, imageVector: ImageVector?, title: String, details: String
) {
    Column(modifier = modifier) {
        CustomTextWithIconUI(imageVector, title, textSize = 20)
        VerticalSpacer(size = 2)
        TextUI(
            modifier = Modifier.padding(8.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            textSize = 16,
            value = details ?: "UnAvailable"
        )
    }
}

@Composable
fun SubscriptionPlanUI(userDetails: ProfileResponse?) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        TextUI(
            textStyle = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
            textSize = 32,
            value = "Subscription plan"
        )

        TextUI(
            textStyle = MaterialTheme.typography.bodyMedium,
            textSize = 18,
            value = "Plan : " + userDetails?.subscription?.plan ?: "No Available"
        )

        TextUI(
            textStyle = MaterialTheme.typography.bodyMedium,
            textSize = 18,
            value = "Status : " + userDetails?.subscription?.status ?: "No Available"
        )

    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun UserPostUI(userPostItem: UserPostsResponse?, onPostClicked: (String) -> Unit) {

    Column(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                userPostItem?.let {
                    onPostClicked(userPostItem.id)
                }
            },
        verticalArrangement = Arrangement.SpaceBetween,
        Alignment.CenterHorizontally,
    ) {
        ProfileImage(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(BorderStroke(1.dp, Color.Gray)),
            profileImage = userPostItem?.postImageUrl
        )

        Text(text = userPostItem?.author ?: "Default name")
    }
}


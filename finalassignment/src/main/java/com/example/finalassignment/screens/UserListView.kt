package com.example.finalassignment.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.finalassignment.R
import com.example.finalassignment.model.ProfileResponse
import com.example.finalassignment.ui.components.LoadingProgressBar
import com.example.finalassignment.ui.components.ProfileImage
import com.example.finalassignment.ui.components.RetryItem
import com.example.finalassignment.ui.components.RoundShapedImage

@Composable
fun UserListView(
    modifier: Modifier = Modifier,
    users: LazyPagingItems<ProfileResponse>,
    onUserClick: (ProfileResponse) -> Unit
) {

    val context = LocalContext.current
    LazyVerticalGrid(
        modifier = modifier.background(MaterialTheme.colorScheme.primary)
            .fillMaxSize(),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        pagingItems(users) { userProfile ->
            userProfile?.let {
                ProfileItem(userProfile) { profile ->
                    onUserClick(profile)
                }
            }
        }
        /*items(users.itemCount, span = { GridItemSpan(1) }) { index ->
            val profile = users[index]
            profile?.let {

            }
        }*/
    }

    users.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                LoadingProgressBar()
            }

            loadState.append is LoadState.Loading -> {
                LoadingProgressBar()
            }

            loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && this.itemCount < 1 -> {
                Toast.makeText(context, "Error has occured.. retry", Toast.LENGTH_SHORT).show()
            }

            loadState.refresh is LoadState.Error -> {
                Toast.makeText(context, "Error has occured.. retry", Toast.LENGTH_SHORT).show()
            }

            loadState.append is LoadState.Error -> {
                Toast.makeText(context, "Error has occured.. retry", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun ProfileItem(profile: ProfileResponse, onUserClick: (ProfileResponse) -> Unit) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                onUserClick(profile)
            },
        verticalArrangement = Arrangement.SpaceBetween,
        Alignment.CenterHorizontally,
    ) {
        RoundShapedImage(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            profileImage = profile?.profileImageUrl
        )
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


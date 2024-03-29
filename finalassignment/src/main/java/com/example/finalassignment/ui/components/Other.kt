package com.example.finalassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
//import com.google.android.material.R
import com.example.finalassignment.R
import com.example.finalassignment.utils.shimmerBrush

@Composable
fun VerticalSpacer(size: Int) = Spacer(modifier = Modifier.height(size.dp))

@Composable
fun HorizontalSpacer(size: Int) = Spacer(modifier = Modifier.width(size.dp))

@Composable
fun ProfileImage(modifier: Modifier = Modifier, profileImage: String?) {
    val showShimmer = remember { mutableStateOf(true) }
    AsyncImage(
        modifier = modifier.background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(LocalContext.current).data(profileImage).crossfade(true)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(com.google.android.material.R.drawable.mtrl_ic_error).build(),
        onSuccess = { showShimmer.value = false },
        contentDescription = null,
    )
}

@Composable
fun RoundedCornerImage(modifier: Modifier = Modifier, profileImage: String?) {
    val showShimmer = remember { mutableStateOf(true) }
    AsyncImage(
        modifier = modifier.background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(LocalContext.current).data(profileImage).crossfade(true)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(com.google.android.material.R.drawable.mtrl_ic_error).build(),
        onSuccess = { showShimmer.value = false },
        contentDescription = null,
    )
}

@Composable
fun RoundShapedImage(modifier: Modifier = Modifier, profileImage: String?) {
    val showShimmer = remember { mutableStateOf(true) }
    AsyncImage(
        modifier = modifier.background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(LocalContext.current).data(profileImage).crossfade(true)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(com.google.android.material.R.drawable.mtrl_ic_error).build(),
        onSuccess = { showShimmer.value = false },
        contentDescription = null,
    )
}

@Composable
fun LoadingProgressBar() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally))
    }
}

@Composable
fun LoadingProgressBar(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 8.dp
) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size)
                .align(Alignment.Center),
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

@Composable
fun RetryItem(
    modifier: Modifier = Modifier, errorMsg: String, onRetryClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(onClick = onRetryClick) {
            Text(text = errorMsg)
        }
    }
}


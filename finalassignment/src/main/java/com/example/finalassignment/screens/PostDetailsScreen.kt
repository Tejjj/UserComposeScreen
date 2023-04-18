package com.example.finalassignment.screens

import android.content.Context
import android.icu.text.DateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.finalassignment.R
import com.example.finalassignment.navigation.TopLevelDestination
import com.example.finalassignment.screens.viewModel.PostDetailsViewModel
import com.example.finalassignment.ui.components.HorizontalSpacer
import com.example.finalassignment.ui.components.ProfileImage
import com.example.finalassignment.ui.components.VerticalSpacer
import java.time.Instant
import java.util.Date
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsScreen(
    viewModel: PostDetailsViewModel,
    userId: String,
    postId: String,
    navController: NavHostController
) {

    val context = LocalContext.current
    var userDetails = remember { viewModel.postDetails }.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPostDetail(userId = userId, postId)
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
                        text = TopLevelDestination.PostDetail.title,
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
                actions = {
                    IconButton(onClick = {
                        userDetails.value?.let {
                            viewModel.deletePost(userId, postId)
                            navController.popBackStack()
                        }

                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null
                        )
                    }
                }
            )
        }, containerColor = MaterialTheme.colorScheme.secondary
    ) { innerPadding ->

        var userInfo = userDetails.value
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(paddingValues = PaddingValues(16.dp))
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(PaddingValues(horizontal = 8.dp, vertical = 8.dp)),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ProfileImage(
                        modifier = Modifier
                            .size(300.dp)
                            .clip(CircleShape)
                            .border(BorderStroke(1.dp, Color.Gray)),
                        profileImage = userInfo?.postImageUrl
                    )

                    VerticalSpacer(size = 16)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextUI(
                                color = MaterialTheme.colorScheme.secondary,
                                textStyle = MaterialTheme.typography.bodyLarge,
                                textSize = 22,
                                value = userInfo?.likesCount.toString()
                            )

                            TextUI(
                                color = MaterialTheme.colorScheme.tertiary,
                                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                                textSize = 26,
                                value = "Likes"
                            )
                        }


                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            TextUI(
                                color = MaterialTheme.colorScheme.secondary,
                                textStyle = MaterialTheme.typography.bodyLarge,
                                textSize = 22,
                                value = userInfo?.commentsCount.toString()
                            )

                            TextUI(
                                color = MaterialTheme.colorScheme.tertiary,
                                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                                textSize = 26,
                                value = "Comments"
                            )
                        }
                    }


                    VerticalSpacer(size = 16)

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        TextUI(
                            modifier = Modifier.alignByBaseline(),
                            color = MaterialTheme.colorScheme.tertiary,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                            textSize = 26,
                            value = "Caption : "
                        )
                        HorizontalSpacer(size = 2)

                        TextUI(
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.alignByBaseline(),
                            textStyle = MaterialTheme.typography.bodyMedium,
                            textSize = 22,
                            value = userInfo?.caption.toString()
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextUI(
                            modifier = Modifier.alignByBaseline(),
                            color = MaterialTheme.colorScheme.secondary,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                            textSize = 26,
                            value = "Created by  : "
                        )
                        HorizontalSpacer(size = 2)

                        userInfo?.let {
                            TextUI(
                                color = MaterialTheme.colorScheme.tertiary,
                                textStyle = MaterialTheme.typography.bodyMedium,
                                textSize = 22,
                                value = getReadableDate(userInfo.createdAt)
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getReadableDate(dateString: String): String {
    return try {
        val date = Date.from(Instant.parse(dateString))
        DateFormat.getDateInstance(DateFormat.MEDIUM).format(date)
    } catch (exception: Exception) {
        "Error Value"
    }

}



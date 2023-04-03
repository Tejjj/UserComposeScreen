package com.example.assignment5.screens

import android.content.ContentValues
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.assignment5.R
import com.example.assignment5.screens.viewModel.PostDetailViewModel
import com.example.assignment5.ui.components.HorizontalSpacer
import com.example.assignment5.ui.components.ProfileImage
import com.example.assignment5.ui.components.VerticalSpacer
import com.example.assignment5.utils.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsScreen(
    modifier: Modifier,
    viewModel: PostDetailViewModel,
    userId: String,
    navController: NavHostController,
    onNavigateUp: () -> Unit
) {

    var uriHandler = LocalUriHandler.current
    var userDetails = remember { viewModel.userProfile }.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserById(userId = userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(modifier = modifier
                .fillMaxWidth()
                .border(BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant)), title = {
                Text(
                    text = AppScreen.UserPostDetailScreen.route,
                    style = MaterialTheme.typography.headlineLarge
                )
            }, navigationIcon = {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        modifier = Modifier.size(64.dp)
                    )
                }
            })
        }, containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->

        var userInfo = userDetails.value
        Card(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .padding(paddingValues = PaddingValues(16.dp))
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
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

                VerticalSpacer(size = 12)

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextUI(
                            color = Color.Blue,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                            textSize = 26,
                            value = "Author : "
                        )
                        HorizontalSpacer(size = 2)
                        TextUI(
                            textStyle = MaterialTheme.typography.bodyMedium,
                            textSize = 22,
                            value = userInfo?.author ?: stringResource(R.string.not_available)
                        )
                    }


                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextUI(
                                color = Color.Blue,
                                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                                textSize = 26,
                                value = "Width : "
                            )
                            HorizontalSpacer(size = 2)

                            TextUI(
                                textStyle = MaterialTheme.typography.bodyMedium,
                                textSize = 22,
                                value = userInfo?.width.toString()
                            )
                        }

                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextUI(
                                color = Color.Blue,
                                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                                textSize = 26,
                                value = "Height : "
                            )
                            HorizontalSpacer(size = 2)

                            TextUI(
                                textStyle = MaterialTheme.typography.bodyMedium,
                                textSize = 22,
                                value = userInfo?.height.toString()
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        userInfo?.let {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextUI(
                                    color = Color.Blue,
                                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                                    textSize = 26,
                                    value = "URL : "
                                )
                                HorizontalSpacer(size = 2)

                                TextUI(
                                    modifier = Modifier.clickable { uriHandler.openUri(userInfo.postImageUrl) },
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    textSize = 22,
                                    value = userInfo?.postImageUrl
                                        ?: stringResource(R.string.not_available)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}
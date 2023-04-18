package com.example.finalassignment.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import com.example.finalassignment.R
import com.example.finalassignment.mode.PostDetailResponse
import com.example.finalassignment.navigation.TopLevelDestination
import com.example.finalassignment.screens.viewModel.CreatePostViewModel
import com.example.finalassignment.utils.Constants
import java.util.Calendar
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    viewModel: CreatePostViewModel,
    userId: String,
    navController: NavHostController
) {

    val context = LocalContext.current
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
        }
    ) { innerPadding ->
        var imageUrl by rememberSaveable { mutableStateOf("") }
        var caption by rememberSaveable { mutableStateOf("") }
        var likes by rememberSaveable { mutableStateOf("") }
        var comments by rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier
                    .padding(PaddingValues(8.dp))
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(2.dp)),
                value = imageUrl,
                onValueChange = {
                    imageUrl = it
                },
                colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
                placeholder = {
                    Text(text = "URL")
                },
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier
                    .padding(PaddingValues(8.dp))
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(2.dp)),
                value = caption,
                onValueChange = {
                    caption = it
                },
                colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
                placeholder = {
                    Text(text = "Caption")
                },
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(PaddingValues(8.dp))
                        .heightIn(min = 56.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(2.dp)),
                    value = likes,
                    onValueChange = {
                        likes = it
                    },
                    colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
                    placeholder = {
                        Text(text = "Likes")
                    },
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                    )
                )

                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(PaddingValues(8.dp))
                        .heightIn(min = 56.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(2.dp)),
                    value = comments,
                    onValueChange = {
                        comments = it
                    },
                    colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
                    placeholder = {
                        Text(text = "Comments")
                    },
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .width(220.dp)
                    .height(64.dp)
                    .background(MaterialTheme.colorScheme.secondary)
                    .align(Alignment.CenterHorizontally),

                shape = RoundedCornerShape(size = 10.dp),
                colors = ButtonDefaults.buttonColors(),
                onClick = {
                    val post = createPost(userId, imageUrl, caption, likes, comments, context)
                    post?.let {
                        viewModel.createPost(post)
                        navController.popBackStack()
                    }
                },
            ) {
                Text(
                    text = stringResource(R.string.create_post),
                    textAlign = TextAlign.Center
                )
            }

        }
    }

}

fun createPost(
    userId: String,
    imageUrl: String,
    caption: String,
    likes: String,
    comments: String,
    context: Context
): PostDetailResponse? {

    if (!likes.isValidInteger()) {
        Toast.makeText(context, "Please enter the correct integer value", Toast.LENGTH_SHORT).show()
        return null
    }

    if (!comments.isValidInteger()) {
        Toast.makeText(context, "Please enter the correct integer value", Toast.LENGTH_SHORT).show()
        return null
    }

    if(caption.isNullOrEmpty()) {
        //Toast.makeText(context, "Invalid Comments value.. please enter correct integer value", Toast.LENGTH_SHORT).show()
        return null
    }

    val post = PostDetailResponse(
        postId = Random.nextInt(10, 888).toString(),
        userId = userId,
        postImageUrl = "",
        caption = caption ?: "",
        likesCount = likes?.toInt() ?: 0,
        commentsCount = comments?.toInt() ?: 0,
        createdAt = Constants.currentDate
    )
    return post
}

fun String.isValidInteger(): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    if (this != null && this.isDigitsOnly()) {
        return this.matches(regex)
    }
    return false
}

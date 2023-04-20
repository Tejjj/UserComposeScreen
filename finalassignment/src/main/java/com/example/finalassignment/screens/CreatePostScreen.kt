package com.example.finalassignment.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.finalassignment.R
import com.example.finalassignment.mode.PostDetailResponse
import com.example.finalassignment.navigation.TopLevelDestination
import com.example.finalassignment.screens.viewModel.CreatePostViewModel
import com.example.finalassignment.ui.components.HorizontalSpacer
import com.example.finalassignment.utils.Constants
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    viewModel: CreatePostViewModel, userId: String, navController: NavHostController
) {

    val context = LocalContext.current
    Scaffold(topBar = {
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
    }) { innerPadding ->
        var imageUrl by rememberSaveable { mutableStateOf<Uri?>(null) }
        var caption by rememberSaveable { mutableStateOf("") }
        var likes by rememberSaveable { mutableStateOf("") }
        var comments by rememberSaveable { mutableStateOf("") }

        var singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                imageUrl = uri
                Log.d("PhotoChooserTejj ", " imageUrl is :: ${imageUrl.toString()} ")
            }
        )

        var chooseImageMessage by rememberSaveable {
            mutableStateOf("")
        }
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                //ImageUriRequestUI()
                Row(
                    modifier = Modifier.padding(PaddingValues(horizontal = 8.dp, vertical = 16.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            //.fillMaxWidth()
                            //.width(IntrinsicSize.Min)
                            .heightIn(min = 56.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(2.dp)),
                        value = imageUrl?.toString()?:"",
                        onValueChange = {
                            //imageUrl = it
                        },
                        colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
                        placeholder = {
                            Text(text = "Image URL")
                        },
                        singleLine = false,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        )
                    )

                    HorizontalSpacer(size = 8)

                    Button(modifier = Modifier/*.weight(1f)*/
                        .background(Color.White)
                        .width(160.dp)
                        .height(56.dp)
                        .border(BorderStroke(3.dp, MaterialTheme.colorScheme.primary))
                        //.align(Alignement.CenterVertically)
                        ,shape = RoundedCornerShape(size = 6.dp),
                        colors = ButtonDefaults.buttonColors(Color.White)

                        ,onClick = {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }) {
                        Text(
                            modifier = Modifier.background(Color.White)
                                .align(Alignment.CenterVertically),
                            text = stringResource(id = R.string.choose_image),
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontSize = 12.sp,
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                imageUrl?.let {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(88.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
                            .crossfade(true).placeholder(R.drawable.ic_launcher_foreground)
                            .error(com.google.android.material.R.drawable.mtrl_ic_error).build(),
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }


            item {
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
            }

            item {
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
            }

            item {
                Button(
                    modifier = Modifier
                        .width(220.dp)
                        .height(64.dp)
                        .background(MaterialTheme.colorScheme.secondary),

                    shape = RoundedCornerShape(size = 4.dp),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    onClick = {
                        val post =
                            createPost(userId, imageUrl.toString(), caption, likes, comments, context)
                        post?.let {
                            viewModel.createPost(post)
                            navController.popBackStack()
                        }
                    },
                ) {
                    Text(
                        text = stringResource(R.string.create_post), textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
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

    if (caption.isNullOrEmpty()) {
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

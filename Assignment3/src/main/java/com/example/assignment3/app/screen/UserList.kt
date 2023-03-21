package com.example.assignment3.app.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.assignment3.R
import com.example.assignment3.app.viewModel.UserViewModel
import com.example.assignment3.repository.User
import com.example.assignment3.repository.UserData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserViewModel = hiltViewModel(),
    addUserEntry: () -> Boolean,
    onUserItemClicked: (Int) -> Unit
) {

    val userList by remember { viewModel.userList }.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getObservableUserList()
    }

    Scaffold(
        topBar = {
            UserAppBar(
                title = AppScreen.UserDirectory.route,
                canNavigateBack = false
            )
        }
    ) { innerPadding ->
        DisplayUserList(Modifier.padding(innerPadding), userList, addUserEntry = {
            (addUserEntry()).let { }
        }) { userId ->
            onUserItemClicked(userId)
        }

    }
}

@Composable
fun DisplayUserList(
    modifier: Modifier = Modifier, userList: List<User>, addUserEntry: () -> Unit,
    onUserItemClicked: (Int) -> Unit
) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(top = 8.dp)) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(id = R.string.welcome_msg), fontSize = 24.sp)
        }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(userList) { index: Int, user: User ->
                CardPlaceholder(index, user) {
                    onUserItemClicked(user.userId)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                addUserEntry()
            }) {
                Text(
                    text = stringResource(id = R.string.btn_add_user),
                    fontSize = 28.sp,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardPlaceholder(index: Int, item: User, onItemClick: (User) -> Unit) {
    Card(
        onClick = {
            onItemClick(item)
        },
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(3.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        UserEntry(index = index, item = item)
    }
}

@Composable
fun UserEntry(index: Int, item: User, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        UserInfo(item, modifier.weight(1.1f))
        Spacer(modifier = Modifier.width(2.dp))
        UserPosition(index, item, modifier.weight(0.9f))
    }
}

@Composable
fun UserPosition(index: Int, userItem: User, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp)
            .paddingFromBaseline(top = 8.dp)
    ) {
        UserTextComposable(
            "username: ${userItem.name}", modifier = Modifier
                .align(Alignment.Start)
        )

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(Color.White),
            shape = CircleShape,
            contentPadding = PaddingValues(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
            border = BorderStroke(3.dp, Color.Black),
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = (index + 1).toString(),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge
            )

        }
    }
}

@Composable
fun UserInfo(userItem: User, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(start = 16.dp)
            .paddingFromBaseline(top = 8.dp)
    ) {
        UserTextComposable(
            "UserId: ${userItem.userId}",
            modifier = Modifier.align(Alignment.Start)
        )

        UserTextComposable(
            "fullName: ${userItem.fullname}",
            modifier = Modifier.align(Alignment.Start)
        )

        UserTextComposable(
            "email: ${userItem.email}",
            modifier = Modifier.align(Alignment.Start)
        )

    }
}

@Composable
fun UserTextComposable(displayValue: String, modifier: Modifier) {
    Text(
        text = displayValue,
        fontSize = 14.sp, modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}
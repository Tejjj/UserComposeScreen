package com.example.usercomposescreen.app.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.usercomposescreen.data.User
import com.example.usercomposescreen.data.UserData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(modifier: Modifier = Modifier) {
    var itemList = UserData.posts.toList()
    val mutableUserList = remember { itemList.toMutableStateList() }

    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
        itemsIndexed(mutableUserList) { index: Int, item: User ->
            Card(onClick = {
                if (index >= 0) {
                    mutableUserList.removeAt(index)
                }
            },
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(3.dp, Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                UserEntry(index = index, item = item)
            }
        }
    }
}

@Composable
fun UserEntry(index: Int, item: User) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        UserInfo(item)
        Spacer(modifier = Modifier.width(8.dp))
        UserPosition(index, item)
    }
}

@Composable
fun UserPosition(index: Int, userItem: User) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .widthIn(min = 64.dp)
            .paddingFromBaseline(top = 8.dp)
    ) {
        UserTextComposable(userItem.fullName,
            modifier = Modifier.paddingFromBaseline(top = 8.dp))

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
fun UserInfo(userItem: User) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 16.dp)
            .paddingFromBaseline(top = 8.dp)
    ) {
        UserTextComposable("UserId: ${userItem.userId}",
            modifier = Modifier.align(Alignment.Start))

        UserTextComposable("UserId: ${userItem.fullName}",
            modifier = Modifier.align(Alignment.Start))

        UserTextComposable("UserId: ${userItem.email}",
            modifier = Modifier.align(Alignment.Start))

    }
}

@Composable
fun UserTextComposable(displayValue: String, modifier: Modifier) {
    Text(text = displayValue, modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1)
}

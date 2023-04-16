package com.example.finalassignment.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.finalassignment.R
import com.example.finalassignment.model.UserProfileResponse
import com.example.finalassignment.ui.components.RoundShapedImage


@Composable
fun UserProfileUI(modifier: Modifier = Modifier, userDetails: UserProfileResponse?) {
    userDetails?.let {
        Box(modifier = modifier) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.Start
            ) {
                ProfileUI(userDetails)

                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextUI(
                        modifier = Modifier.alignByBaseline(),
                        color = MaterialTheme.colorScheme.secondary,
                        textStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        textSize = 18,
                        value = stringResource(R.string.name)
                    )
                    TextUI(
                        modifier = Modifier.alignByBaseline(),
                        color = MaterialTheme.colorScheme.tertiary,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        textSize = 12,
                        value = userDetails.name
                    )
                }

                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextUI(
                        modifier = Modifier.alignByBaseline(),
                        color = MaterialTheme.colorScheme.secondary,
                        textStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        textSize = 18,
                        value = stringResource(R.string.about)
                    )
                    TextUI(
                        modifier = Modifier.alignByBaseline(),
                        color = MaterialTheme.colorScheme.tertiary,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        textSize = 12,
                        value = userDetails.name
                    )
                }

                Divider(
                    modifier = Modifier
                        .padding(PaddingValues(vertical = 8.dp))
                        .height(4.dp)
                        .fillMaxWidth(), color = Color.Black
                )
            }
        }
    }

}


@Composable
fun ProfileUI(userDetails: UserProfileResponse) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundShapedImage(
            modifier = Modifier
                .weight(0.3f)
                .size(100.dp)
                .clip(CircleShape),
            profileImage = userDetails?.profileImageUrl
        )
        Row(
            modifier = Modifier
                .weight(0.7f)
                .padding(PaddingValues(start = 16.dp, end = 4.dp)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Posts
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    color = MaterialTheme.colorScheme.tertiary,
                    text = userDetails?.posts.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )

                Text(
                    color = MaterialTheme.colorScheme.secondary,
                    text = stringResource(R.string.posts),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            // Followers
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    color = MaterialTheme.colorScheme.tertiary,
                    text = userDetails?.posts.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )

                Text(
                    color = MaterialTheme.colorScheme.secondary,
                    text = stringResource(R.string.followers),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            //Following
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    color = MaterialTheme.colorScheme.tertiary,
                    text = userDetails?.posts.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )

                Text(
                    color = MaterialTheme.colorScheme.secondary,
                    text = stringResource(R.string.following),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
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

package com.example.assignment3.app.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.assignment3.R


sealed class AppScreen(
    val route: String,
    @DrawableRes val selectedIcon: Int
) {
    object UserDirectory :
        AppScreen("UserDirectory", selectedIcon = R.drawable.ic_launcher_foreground)

    object MainScreen : AppScreen("Main", selectedIcon = R.drawable.ic_launcher_foreground)
    object UserListScreen : AppScreen("Users", selectedIcon = R.drawable.ic_launcher_foreground)
    object UserDetailScreen :
        AppScreen("UserDetail", selectedIcon = R.drawable.ic_launcher_foreground)

    object LoginScreen : AppScreen("Login", selectedIcon = R.drawable.ic_launcher_foreground)

    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAppBar(
    title: String,
    navIcon: ImageVector = Icons.Filled.ArrowBack,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    if (canNavigateBack) {
        TopAppBar(
            title = { Text(text = title, style = MaterialTheme.typography.headlineLarge) },
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = navIcon,
                        contentDescription = stringResource(R.string.back_button),
                        modifier = Modifier.size(64.dp)
                    )
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .border(BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant))
        )
    } else {
        TopAppBar(
            title = { Text(text = title, style = MaterialTheme.typography.headlineLarge) },
            modifier = modifier
                .fillMaxWidth()
                .border(BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant))
        )
    }
}


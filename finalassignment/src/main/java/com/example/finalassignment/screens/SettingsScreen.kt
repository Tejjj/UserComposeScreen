package com.example.finalassignment.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.finalassignment.R
import com.example.finalassignment.navigation.TopLevelDestination
import com.example.finalassignment.screens.viewModel.SettingsViewModel
import com.example.finalassignment.ui.components.HorizontalSpacer
import com.example.finalassignment.ui.components.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val currentLanguage by remember { settingsViewModel.language }.collectAsState()

    Scaffold(topBar = {
        TopAppBar(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant)),

            title = {
                Text(
                    text = TopLevelDestination.Settings.title,
                    style = MaterialTheme.typography.headlineLarge
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        modifier = Modifier.size(64.dp)
                    )
                }
            })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.app_language),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            )
            VerticalSpacer(size = 8)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AssistChip(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        settingsViewModel.saveLanguage("en")
                        navController.popBackStack()
                    },
                    label = {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            textAlign = TextAlign.Center,
                            fontSize = 28.sp,
                            text = stringResource(R.string.english)
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (currentLanguage == "en") MaterialTheme.colorScheme.secondary
                        else MaterialTheme.colorScheme.background
                    )
                )
                HorizontalSpacer(size = 8)

                AssistChip(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        settingsViewModel.saveLanguage("hi")
                        navController.popBackStack()
                    },
                    label = {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            textAlign = TextAlign.Center,
                            fontSize = 28.sp,
                            text = stringResource(R.string.hindi)
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (currentLanguage == "hi") MaterialTheme.colorScheme.secondary
                        else MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}

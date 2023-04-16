package com.example.finalassignment.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.finalassignment.ui.theme.UserComposeScreenTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavApp() {
    UserComposeScreenTheme {
        val navController = rememberNavController()

        Scaffold(
            topBar = {

            },
            bottomBar = {
                AppBottomBar(
                    destinationList = bottomNavItems,
                    onNavigateToDestination = { destination ->
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    currentDestination = navController.currentBackStackEntryAsState().value?.destination
                )
            }
        ) { innerPadding ->
            UserNavHost(modifier = Modifier.padding(innerPadding), navController = navController)
        }
    }
}

@Composable
fun AppBottomBar(
    destinationList: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar {
        destinationList.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon =
                        if (selected) destination.selectedIcon else destination.unselectedIcon

                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = destination.route
                    )
                },
                label = { Text(text = destination.title) },
                alwaysShowLabel = false
            )
        }
    }
}


fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean =
    this?.hierarchy?.any {
        it.route?.contains(destination.route, true) ?: false
    } ?: false
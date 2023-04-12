package com.far.goestochecklist.ui.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.far.goestochecklist.ui.navigation.Routes.*
import com.far.goestochecklist.ui.theme.Gray200
import com.far.goestochecklist.ui.theme.Gray800
import com.far.goestochecklist.ui.theme.Yellow

/*
 * Created by Filipi Andrade Rocha on 10/04/2023.
 */

@Composable
fun GoesToChecklistBottomNavigation(
	navController: NavController
) {

	var bottomNavigationVisibility by remember { mutableStateOf(true) }
	val activity = LocalContext.current as Activity

	val items = listOf(
		BottomMenuItem.Home,
		BottomMenuItem.Search,
		BottomMenuItem.ProfileMenu
	)

	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentRoute = navBackStackEntry?.destination?.route

	bottomNavigationVisibility = when (navBackStackEntry?.destination?.route) {
		Home.route, Search.route, ProfileMenu.route -> true
		else -> false
	}

	AnimatedVisibility(
		visible = bottomNavigationVisibility
	) {
		BottomNavigation(backgroundColor = Gray800) {
			items.forEach { item ->
				BottomNavigationItem(
					icon = {
						Icon(
							modifier = Modifier.size(24.dp),
							painter = painterResource(id = item.icon),
							contentDescription = item.label
						)
					},
					label = {
						Text(
							text = item.label,
							style = MaterialTheme.typography.overline
						)
					},
					selectedContentColor = Yellow,
					unselectedContentColor = Gray200,
					alwaysShowLabel = true,
					selected = currentRoute == item.route.route,
					onClick = {
						if (currentRoute != item.route.route) {
							navController.navigate(item.route.route) {
								navController.graph.startDestinationRoute?.let { screen_route ->
									popUpTo(screen_route) {
										saveState = true
									}
								}
								launchSingleTop = true
								restoreState = true
							}
						}
					}
				)
			}
		}
	}

	BackHandler {
		if (currentRoute == Home.route) {
			activity.finishAffinity()
		}
	}
}
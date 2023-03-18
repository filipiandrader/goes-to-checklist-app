package com.far.goestochecklist.ui.orchestrator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.far.goestochecklist.presentation.MainEvent
import com.far.goestochecklist.presentation.MainViewModel
import com.far.goestochecklist.ui.navigation.Routes
import com.far.goestochecklist.ui.navigation.doNavigation

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

@Composable
fun OrchestratorScreen(
	navController: NavController,
	viewModel: MainViewModel = hiltViewModel(),
) {
	LaunchedEffect(key1 = true) {
		viewModel.mainEventChannel.collect {
			when (it) {
				is MainEvent.UserLogged -> doNavigation(Routes.Home, navController)
				else -> doNavigation(Routes.Welcome, navController)
			}
		}
	}
}

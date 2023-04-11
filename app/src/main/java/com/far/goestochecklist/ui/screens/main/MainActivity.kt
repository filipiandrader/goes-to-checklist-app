@file:OptIn(ExperimentalAnimationApi::class)

package com.far.goestochecklist.ui.screens.main

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.far.goestochecklist.common.orFalse
import com.far.goestochecklist.presentation.main.MainViewModel
import com.far.goestochecklist.ui.navigation.Routes
import com.far.goestochecklist.ui.screens.login.LoginScreen
import com.far.goestochecklist.ui.screens.orchestrator.OrchestratorScreen
import com.far.goestochecklist.ui.screens.signup.SignUpScreen
import com.far.goestochecklist.ui.screens.welcome.WelcomeScreen
import com.far.goestochecklist.ui.theme.GoesToChecklistTheme
import com.far.goestochecklist.ui.theme.Gray900
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	private val viewModel by viewModels<MainViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		installSplashScreen()
		val content = findViewById<View>(android.R.id.content)
		content.viewTreeObserver.addOnPreDrawListener {
			viewModel.showSplashScreenViewState.value?.data.orFalse()
		}

		setContent {
			GoesToChecklistTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = Gray900,
				) {
					SetupNavigation()
				}
			}
		}
	}
}

@Composable
fun SetupNavigation() {
	val navController = rememberAnimatedNavController()

	AnimatedNavHost(
		navController = navController,
		startDestination = Routes.Splash.route,
	) {
		composable(Routes.Splash.route) {
			OrchestratorScreen(navController = navController)
		}
		composable(Routes.Welcome.route) {
			WelcomeScreen(navController = navController)
		}
		composable(
			Routes.Login.route,
			enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
			exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
			popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
			popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
		) {
			LoginScreen(navController = navController)
		}
		composable(
			Routes.SignUp.route,
			enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
			exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
			popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
			popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
		) {
			SignUpScreen(navController = navController)
		}
		composable(Routes.Main.route) {
			MainScreen(navController = navController)
		}
	}
}

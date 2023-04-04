@file:OptIn(ExperimentalAnimationApi::class)

package com.far.goestochecklist.ui.screens.main

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.far.goestochecklist.common.Constants.FILM_QUERY_NAME
import com.far.goestochecklist.common.Constants.USER_QUERY_NAME
import com.far.goestochecklist.common.fromJson
import com.far.goestochecklist.common.orFalse
import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.presentation.main.MainViewModel
import com.far.goestochecklist.ui.navigation.Routes
import com.far.goestochecklist.ui.screens.film.FilmDetailScreen
import com.far.goestochecklist.ui.screens.home.HomeScreen
import com.far.goestochecklist.ui.screens.login.LoginScreen
import com.far.goestochecklist.ui.screens.orchestrator.OrchestratorScreen
import com.far.goestochecklist.ui.screens.profile.EditProfileDataScreen
import com.far.goestochecklist.ui.screens.profile.ProfileScreen
import com.far.goestochecklist.ui.screens.signup.SignUpScreen
import com.far.goestochecklist.ui.screens.welcome.WelcomeScreen
import com.far.goestochecklist.ui.theme.GoesToChecklistTheme
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
					color = MaterialTheme.colors.background,
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
		composable(Routes.Home.route) {
			HomeScreen(navController = navController)
		}
		composable(
			Routes.FilmDetail.route,
			enterTransition = { slideInVertically(initialOffsetY = { 1000 }) },
			exitTransition = {
				slideOutVertically(
					targetOffsetY = { -1000 },
					animationSpec = tween(150, easing = LinearEasing)
				)
			},
			popEnterTransition = { slideInVertically(initialOffsetY = { -1000 }) },
			popExitTransition = {
				slideOutVertically(
					targetOffsetY = { 1000 },
					animationSpec = tween(150, easing = LinearEasing)
				)
			}
		) { navBackStackEntry ->
			val filmJson = navBackStackEntry.arguments?.getString(FILM_QUERY_NAME)
			val film = filmJson?.fromJson<Film>()
			film?.let { FilmDetailScreen(navController = navController, film = it) }
		}
		composable(
			Routes.Profile.route,
			enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
			exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
			popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
			popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
		) { navBackStackEntry ->
			val userJson = navBackStackEntry.arguments?.getString(USER_QUERY_NAME)
			val user = userJson?.fromJson<Login>()
			user?.let { ProfileScreen(navController = navController, it) }
		}
		composable(
			Routes.EditProfileData.route,
			enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
			exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
			popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
			popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
		) { navBackStackEntry ->
			val userJson = navBackStackEntry.arguments?.getString(USER_QUERY_NAME)
			val user = userJson?.fromJson<Login>()
			user?.let { EditProfileDataScreen(navController = navController, it) }
		}
	}
}

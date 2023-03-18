package com.far.goestochecklist.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.far.goestochecklist.common.orFalse
import com.far.goestochecklist.presentation.MainViewModel
import com.far.goestochecklist.ui.login.LoginScreen
import com.far.goestochecklist.ui.navigation.Routes
import com.far.goestochecklist.ui.orchestrator.OrchestratorScreen
import com.far.goestochecklist.ui.signup.SignUpScreen
import com.far.goestochecklist.ui.theme.GoesToChecklistTheme
import com.far.goestochecklist.ui.welcome.WelcomeScreen
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
	val navController = rememberNavController()

	NavHost(
		navController = navController,
		startDestination = Routes.Splash.route,
	) {
		composable(Routes.Splash.route) {
			OrchestratorScreen(navController = navController)
		}
		composable(Routes.Welcome.route) {
			WelcomeScreen(navController = navController)
		}
		composable(Routes.Login.route) {
			LoginScreen(navController = navController)
		}
		composable(Routes.SignUp.route) {
			SignUpScreen(navController = navController)
		}
	}
}

package com.far.goestochecklist.ui.screens.main

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.far.goestochecklist.common.Constants
import com.far.goestochecklist.common.fromJson
import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.ui.navigation.GoesToChecklistBottomNavigation
import com.far.goestochecklist.ui.navigation.Routes
import com.far.goestochecklist.ui.screens.film.FilmDetailScreen
import com.far.goestochecklist.ui.screens.home.HomeScreen
import com.far.goestochecklist.ui.screens.profile.data.ProfileScreen
import com.far.goestochecklist.ui.screens.profile.editdata.EditProfileDataScreen
import com.far.goestochecklist.ui.screens.search.SearchScreen
import com.far.goestochecklist.ui.theme.Gray900
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

/*
 * Created by Filipi Andrade Rocha on 10/04/2023.
 */

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(navController: NavController) {
	val bottomNavController = rememberAnimatedNavController()
	bottomNavController.enableOnBackPressed(true)

	Scaffold(
		modifier = Modifier
			.fillMaxSize()
			.background(Gray900),
		bottomBar = { GoesToChecklistBottomNavigation(navController = bottomNavController) }
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.background(Gray900)
				.padding(it)
		) {
			SetupBottomNavigation(bottomNavController, navController)
		}
	}
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupBottomNavigation(bottomNavController: NavHostController, navController: NavController) {
	AnimatedNavHost(navController = bottomNavController, startDestination = Routes.Home.route) {
		composable(Routes.Home.route) {
			HomeScreen(bottomNavController = bottomNavController)
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
			val filmJson = navBackStackEntry.arguments?.getString(Constants.FILM_QUERY_NAME)
			val film = filmJson?.fromJson<Film>()
			film?.let { FilmDetailScreen(bottomNavController = bottomNavController, film = it) }
		}
		composable(Routes.Search.route) {
			SearchScreen(bottomNavController = bottomNavController)
		}
		composable(
			Routes.Profile.route,
			enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
			exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
			popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
			popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
		) {
			ProfileScreen(navController = navController, bottomNavController = bottomNavController)
		}
		composable(
			Routes.EditProfileData.route,
			enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
			exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
			popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
			popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
		) { navBackStackEntry ->
			val userJson = navBackStackEntry.arguments?.getString(Constants.USER_QUERY_NAME)
			val user = userJson?.fromJson<Login>()
			user?.let { EditProfileDataScreen(bottomNavController = bottomNavController, it) }
		}
	}
}
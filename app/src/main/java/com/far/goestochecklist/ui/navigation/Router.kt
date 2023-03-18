package com.far.goestochecklist.ui.navigation

import androidx.navigation.NavController

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

fun doNavigation(route: Routes, navController: NavController) {
	when (route) {
		Routes.Login -> navigateToLogin(navController, route)
		Routes.SignUp -> navigateToSignUp(navController, route)
		Routes.Home -> navigateToHome(navController, route)
		else -> Unit
	}
}

private fun navigateToLogin(navController: NavController, login: Routes) {
	navController.popBackStack()
	navController.navigate(login.route)
}

private fun navigateToSignUp(navController: NavController, signUp: Routes) {
	navController.navigate(signUp.route)
}

private fun navigateToHome(navController: NavController, home: Routes) {
	navController.popBackStack()
	navController.navigate(home.route)
}

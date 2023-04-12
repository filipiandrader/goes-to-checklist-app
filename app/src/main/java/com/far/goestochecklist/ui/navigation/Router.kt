package com.far.goestochecklist.ui.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.far.goestochecklist.common.Constants.FILM_QUERY
import com.far.goestochecklist.common.Constants.FILM_QUERY_NAME
import com.far.goestochecklist.common.Constants.USER_QUERY
import com.far.goestochecklist.common.Constants.USER_QUERY_NAME
import com.far.goestochecklist.common.printError
import com.far.goestochecklist.domain.exception.EmptyBundleException
import com.far.goestochecklist.domain.model.Film
import com.google.gson.Gson

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

fun doNavigation(route: Routes, navController: NavController, bundle: Bundle? = null) {
	when (route) {
		Routes.Welcome -> navigateToWelcome(navController, route)
		Routes.Login -> navigateToLogin(navController, route)
		Routes.SignUp -> navigateToSignUp(navController, route)
		Routes.Main -> navigateToMain(navController, route)
		Routes.Home -> navigateToHome(navController, route)
		Routes.Search -> navigateToSearch(navController, route)
		Routes.FilmDetail -> navigateToFilmDetail(navController, route, bundle)
		Routes.ProfileMenu -> navigateToProfileMenu(navController, route)
		Routes.ProfileData -> navigateToProfileData(navController, route)
		Routes.EditProfileData -> navigateToEditProfileData(navController, route, bundle)
		Routes.ChangePassword -> navigateToChangePassword(navController, route)
		Routes.Logout -> logout(navController)
		else -> Unit
	}
}

private fun navigateToWelcome(navController: NavController, welcome: Routes) {
	navController.popBackStack()
	navController.navigate(welcome.route)
}

private fun navigateToLogin(navController: NavController, login: Routes) {
	navController.navigate(login.route)
}

private fun navigateToSignUp(navController: NavController, signUp: Routes) {
	navController.navigate(signUp.route)
}

private fun navigateToMain(navController: NavController, main: Routes) {
	navController.navigate(main.route)
}

private fun navigateToHome(navController: NavController, home: Routes) {
	navController.navigate(home.route) { popUpTo(0) }
}

private fun navigateToSearch(navController: NavController, search: Routes) {
	navController.navigate(search.route)
}

private fun navigateToFilmDetail(
	navController: NavController, filmDetail: Routes, bundle: Bundle?
) {
	try {
		bundle?.let {
			val film = it.getParcelable<Film>(FILM_QUERY_NAME)
			navController.navigate(filmDetail.route.replace(FILM_QUERY, Gson().toJson(film)))
		} ?: throw EmptyBundleException()
	} catch (e: Exception) {
		e.printError()
	}
}

private fun navigateToProfileMenu(navController: NavController, profileMenu: Routes) {
	navController.navigate(profileMenu.route)
}

private fun navigateToProfileData(navController: NavController, profileData: Routes) {
	navController.navigate(profileData.route)
}

private fun navigateToEditProfileData(
	navController: NavController, editProfileData: Routes, bundle: Bundle?
) {
	try {
		bundle?.let {
			val user = it.getString(USER_QUERY_NAME)
			navController.navigate(editProfileData.route.replace(USER_QUERY, user.orEmpty()))
		} ?: throw EmptyBundleException()
	} catch (e: Exception) {
		e.printError()
	}
}

private fun navigateToChangePassword(navController: NavController, changePassword: Routes) {
	navController.navigate(changePassword.route)
}

private fun logout(navController: NavController) {
	navController.navigate(Routes.Welcome.route) { popUpTo(0) }
}

package com.far.goestochecklist.ui.navigation

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

sealed class Routes(val route: String) {
	object Splash : Routes("splash_screen")
	object Welcome : Routes("welcome_screen")
	object Login : Routes("login_screen")
	object SignUp : Routes("signup_screen")
	object Home : Routes("home_screen")
	object FilmDetail : Routes("film_detail_screen?film={film}")
	object Profile : Routes("profile_screen")
	object EditProfileData : Routes("edit_profile_data_screen?user={user}")
}

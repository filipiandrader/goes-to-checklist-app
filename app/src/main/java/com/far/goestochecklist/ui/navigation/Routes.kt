package com.far.goestochecklist.ui.navigation

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

sealed class Routes(val route: String) {
	object Splash : Routes("splash_screen")
	object Welcome : Routes("welcome_screen")
	object Login : Routes("login_screen")
	object SignUp : Routes("signup_screen")
	object Main : Routes("main_screen")
	object Home : Routes("home_screen")
	object Search : Routes("search_screen")
	object FilmDetail : Routes("film_detail_screen?film={film}")
	object ProfileMenu : Routes("profile_menu_screen")
	object ProfileData : Routes("profile_data_screen")
	object EditProfileData : Routes("edit_profile_data_screen?user={user}")
	object ChangePassword : Routes("change_password_screen")
	object Logout : Routes("logout")
}

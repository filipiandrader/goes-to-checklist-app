package com.far.goestochecklist.ui.navigation

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

sealed class Routes(val route: String) {
    object Splash : Routes("splash_screen")
    object Login : Routes("login_screen")
    object SignUp : Routes("signup_screen")
    object Home : Routes("home_screen")
}

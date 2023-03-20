package com.far.goestochecklist.presentation.main

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

sealed class MainEvent {
    object GetCurrentUser : MainEvent()
    object UserLogged : MainEvent()
    object UserNotLogged : MainEvent()
}

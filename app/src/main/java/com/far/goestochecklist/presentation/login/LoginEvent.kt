package com.far.goestochecklist.presentation.login

sealed class LoginEvent {
    data class ValidateUsernameSubmit(val username: String) : LoginEvent()
    data class ValidateUsernameSuccess(val username: String) : LoginEvent()
    data class UsernameError(val throwable: Throwable) : LoginEvent()
    data class ValidatePasswordSubmit(val password: String) : LoginEvent()
    data class ValidatePasswordSuccess(val password: String) : LoginEvent()
    data class PasswordError(val throwable: Throwable) : LoginEvent()
    data class ValidToLogin(val valid: Boolean) : LoginEvent()
    object DoLoginSubmit : LoginEvent()
    object LoginSuccess : LoginEvent()
    data class LoginError(val throwable: Throwable) : LoginEvent()
}

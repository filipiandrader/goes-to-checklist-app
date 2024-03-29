package com.far.goestochecklist.presentation.signup

sealed class SignUpEvent {
    data class ValidateNameSubmit(val name: String) : SignUpEvent()
    data class ValidateNameSuccess(val name: String) : SignUpEvent()
    data class ValidateNameError(val throwable: Throwable) : SignUpEvent()
    data class ValidateUsernameSubmit(val username: String) : SignUpEvent()
    data class ValidateUsernameSuccess(val username: String) : SignUpEvent()
    data class ValidateUsernameError(val throwable: Throwable) : SignUpEvent()
    data class ValidatePasswordSubmit(val password: String) : SignUpEvent()
    data class ValidatePasswordSuccess(val password: String) : SignUpEvent()
    data class ValidatePasswordError(val throwable: Throwable) : SignUpEvent()
    data class ValidToLogin(val valid: Boolean) : SignUpEvent()
    object SignUpSubmit : SignUpEvent()
    data class SignUpError(val throwable: Throwable) : SignUpEvent()
	object LoginSuccess : SignUpEvent()
}

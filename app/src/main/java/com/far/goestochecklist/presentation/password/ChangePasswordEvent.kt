package com.far.goestochecklist.presentation.password

/*
 * Created by Filipi Andrade Rocha on 22/04/2023.
 */

sealed class ChangePasswordEvent {
	data class ValidateNewPasswordSubmit(val newPassword: String) : ChangePasswordEvent()
	data class ValidateNewPasswordSuccess(val newPassword: String) : ChangePasswordEvent()
	data class ValidateNewPasswordError(val throwable: Throwable) : ChangePasswordEvent()
	data class ValidateConfirmNewPasswordSubmit(val confirmNewPassword: String) :
		ChangePasswordEvent()

	data class ValidateConfirmNewPasswordSuccess(val confirmNewPassword: String) :
		ChangePasswordEvent()

	data class ValidateConfirmNewPasswordError(val throwable: Throwable) : ChangePasswordEvent()
	data class ValidToChangePassword(val valid: Boolean) : ChangePasswordEvent()
	data class ValidToChangePasswordError(val throwable: Throwable) : ChangePasswordEvent()
	object ChangePasswordSubmit : ChangePasswordEvent()
	object ChangePasswordSuccess : ChangePasswordEvent()
	data class ChangePasswordError(val throwable: Throwable) : ChangePasswordEvent()
}

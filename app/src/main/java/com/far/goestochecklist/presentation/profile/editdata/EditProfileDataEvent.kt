package com.far.goestochecklist.presentation.profile.editdata

import com.far.goestochecklist.domain.model.Login

/*
 * Created by Filipi Andrade Rocha on 04/04/2023.
 */

sealed class EditProfileDataEvent {
	data class UpdateUserInfoSubmit(val login: Login) : EditProfileDataEvent()
	object UpdateUserInfoSuccess : EditProfileDataEvent()
	data class UpdateUserInfoError(val throwable: Throwable) : EditProfileDataEvent()
	data class ValidateNameSubmit(val currentName: String, val newName: String) :
		EditProfileDataEvent()

	data class ValidateNameSuccess(val name: String) : EditProfileDataEvent()
	data class ValidateNameError(val throwable: Throwable) : EditProfileDataEvent()
	data class ValidToUpdate(val valid: Boolean) : EditProfileDataEvent()
}

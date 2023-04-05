package com.far.goestochecklist.presentation.profile.editdata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.goestochecklist.common.isEqualTo
import com.far.goestochecklist.common.isNotNullOrNotEmpty
import com.far.goestochecklist.common.postSuccess
import com.far.goestochecklist.common.viewState
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.usecase.profile.EditProfileDataUseCases
import com.far.goestochecklist.domain.usecase.user.InsertUserUseCase
import com.far.goestochecklist.domain.usecase.user.UpdateUserInfoUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateNameUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateUsernameUseCase
import com.far.goestochecklist.presentation.profile.editdata.EditProfileDataEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 04/04/2023.
 */

@HiltViewModel
class EditProfileDataViewModel @Inject constructor(
	private val editProfileDataUseCases: EditProfileDataUseCases
) : ViewModel() {

	private val _currentNameViewState by viewState<String>()
	private val _currentUsernameViewState by viewState<String>()

	private val _editProfileDataEventChannel = Channel<EditProfileDataEvent>()
	val editProfileDataEventChannel = _editProfileDataEventChannel.receiveAsFlow()

	private var name = ""
	private var username = ""

	fun onEvent(event: EditProfileDataEvent) {
		when (event) {
			is ValidateNameSubmit -> validateName(event.currentName, event.newName)
			is ValidateNameSuccess -> {
				name = event.name
				validToUpdate()
			}
			is ValidateNameError -> sendEvent(event)
			is ValidToUpdate -> sendEvent(event)
			is UpdateUserInfoSubmit -> updateUserInfo(event.login)
			is UpdateUserInfoSuccess -> sendEvent(event)
			is UpdateUserInfoError -> sendEvent(event)
		}
	}

	private fun sendEvent(event: EditProfileDataEvent) {
		viewModelScope.launch { _editProfileDataEventChannel.send(event) }
	}

	private fun validateName(currentName: String, newName: String) {
		_currentNameViewState.postSuccess(currentName)
		editProfileDataUseCases.validateNameUseCase(
			params = ValidateNameUseCase.Params(newName),
			onSuccess = { onEvent(ValidateNameSuccess(newName)) },
			onError = { onEvent(ValidateNameError(it)) }
		)
	}

	private fun validToUpdate() {
		val currentName = _currentNameViewState.value?.data
		val sameName = currentName.isEqualTo(name)
		if (name.isNotNullOrNotEmpty() && !sameName) {
			onEvent(ValidToUpdate(true))
		} else {
			onEvent(ValidToUpdate(false))
		}
	}

	private fun updateUserInfo(login: Login) {
		editProfileDataUseCases.updateUserInfoUseCase(
			params = UpdateUserInfoUseCase.Params(login),
			onSuccess = { updateUserInfoLocally(login) },
			onError = { onEvent(UpdateUserInfoError(it)) }
		)
	}

	private fun updateUserInfoLocally(login: Login) {
		editProfileDataUseCases.insertUserUseCase(
			params = InsertUserUseCase.Params(login),
			onSuccess = { onEvent(UpdateUserInfoSuccess) },
			onError = { onEvent(UpdateUserInfoError(it)) }
		)
	}
}
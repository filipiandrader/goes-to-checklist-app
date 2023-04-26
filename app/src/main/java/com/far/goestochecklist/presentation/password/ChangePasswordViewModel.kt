package com.far.goestochecklist.presentation.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.goestochecklist.domain.usecase.password.ChangePasswordUseCase
import com.far.goestochecklist.domain.usecase.password.ChangePasswordUseCases
import com.far.goestochecklist.domain.usecase.validators.ValidateChangePasswordUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateConfirmNewPasswordUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateNewPasswordUseCase
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ChangePasswordError
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ChangePasswordSubmit
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ChangePasswordSuccess
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidToChangePassword
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidToChangePasswordError
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidateConfirmNewPasswordError
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidateConfirmNewPasswordSubmit
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidateConfirmNewPasswordSuccess
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidateNewPasswordError
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidateNewPasswordSubmit
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidateNewPasswordSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 22/04/2023.
 */

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
	private val changePasswordUseCases: ChangePasswordUseCases
) : ViewModel() {

	private var validNewPassword = ""
	private var validConfirmNewPassword = ""

	private val _changePasswordEventChannel = Channel<ChangePasswordEvent>()
	val changePasswordEventChannel = _changePasswordEventChannel.receiveAsFlow()

	fun onEvent(event: ChangePasswordEvent) {
		when (event) {
			is ChangePasswordSubmit -> changePassword()
			is ChangePasswordSuccess -> sendEvent(event)
			is ChangePasswordError -> sendEvent(event)
			is ValidateNewPasswordSubmit -> validateNewPassword(event.newPassword)
			is ValidateNewPasswordSuccess -> {
//				_validateNewPasswordViewState.postSuccess(event.newPassword)
				validNewPassword = event.newPassword
				validToChangePassword()
			}

			is ValidateNewPasswordError -> sendEvent(event)
			is ValidateConfirmNewPasswordSubmit -> validateConfirmNewPassword(event.confirmNewPassword)
			is ValidateConfirmNewPasswordSuccess -> {
//				_validateConfirmNewPasswordViewState.postSuccess(event.confirmNewPassword)
				validConfirmNewPassword = event.confirmNewPassword
				validToChangePassword()
			}

			is ValidateConfirmNewPasswordError -> sendEvent(event)
			is ValidToChangePassword -> sendEvent(event)
			is ValidToChangePasswordError -> sendEvent(event)
		}
	}

	private fun sendEvent(event: ChangePasswordEvent) {
		viewModelScope.launch { _changePasswordEventChannel.send(event) }
	}

	private fun validateNewPassword(newPassword: String) {
		changePasswordUseCases.validateNewPasswordUseCase(
			params = ValidateNewPasswordUseCase.Params(newPassword),
			onSuccess = { onEvent(ValidateNewPasswordSuccess(newPassword)) },
			onError = { onEvent(ValidateNewPasswordError(it)) }
		)
	}

	private fun validateConfirmNewPassword(confirmNewPassword: String) {
		changePasswordUseCases.validateConfirmNewPasswordUseCase(
			params = ValidateConfirmNewPasswordUseCase.Params(confirmNewPassword),
			onSuccess = { onEvent(ValidateConfirmNewPasswordSuccess(confirmNewPassword)) },
			onError = { onEvent(ValidateConfirmNewPasswordError(it)) }
		)
	}

	private fun validToChangePassword() {
		changePasswordUseCases.validateChangePasswordUseCase(
			params = ValidateChangePasswordUseCase.Params(
				validNewPassword,
				validConfirmNewPassword
			),
			onSuccess = { onEvent(ValidToChangePassword(true)) },
			onError = {
				onEvent(ValidToChangePassword(false))
				if (validNewPassword.isEmpty()) {
					onEvent(ValidateNewPasswordError(it))
				} else if (validConfirmNewPassword.isEmpty()) {
					onEvent(ValidateConfirmNewPasswordError(it))
				} else {
					onEvent(ValidToChangePasswordError(it))
				}
			}
		)
	}

	private fun changePassword() {
		changePasswordUseCases.changePasswordUseCase(
			params = ChangePasswordUseCase.Params(validNewPassword),
			onSuccess = { onEvent(ChangePasswordSuccess) },
			onError = { onEvent(ChangePasswordError(it)) }
		)
	}
}
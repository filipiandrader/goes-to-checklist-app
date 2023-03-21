package com.far.goestochecklist.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.goestochecklist.common.isNotNullOrNotEmpty
import com.far.goestochecklist.common.postSuccess
import com.far.goestochecklist.common.viewState
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.usecase.login.DoLoginUseCase
import com.far.goestochecklist.domain.usecase.signup.SignUpUseCase
import com.far.goestochecklist.domain.usecase.signup.SignUpUseCases
import com.far.goestochecklist.domain.usecase.user.InsertUserUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateNameUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidatePasswordUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateUsernameUseCase
import com.far.goestochecklist.presentation.core.isNeutral
import com.far.goestochecklist.presentation.signup.SignUpEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

@HiltViewModel
class SignUpViewModel @Inject constructor(
	private val signUpUseCases: SignUpUseCases
) : ViewModel() {

	private val _validateNameViewState by viewState<String>()
	private val _validateUsernameViewState by viewState<String>()
	private val _validatePasswordViewState by viewState<String>()

	private val _validationEventChannel = Channel<SignUpEvent>()
	val validationEventChannel = _validationEventChannel.receiveAsFlow()

	fun onEvent(event: SignUpEvent) {
		when (event) {
			is ValidateNameSubmit -> validateName(event.name)
			is ValidateNameSuccess -> {
				_validateNameViewState.postSuccess(event.name)
				validToLogin()
			}
			is NameError -> sendEvent(event)
			is ValidateUsernameSubmit -> validateUsername(event.username)
			is ValidateUsernameSuccess -> {
				_validateUsernameViewState.postSuccess(event.username)
				validToLogin()
			}
			is UsernameError -> sendEvent(event)
			is ValidatePasswordSubmit -> validatePassword(event.password)
			is ValidatePasswordSuccess -> {
				_validatePasswordViewState.postSuccess(event.password)
				validToLogin()
			}
			is PasswordError -> sendEvent(event)
			is ValidToLogin -> sendEvent(event)
			is SignUpSubmit -> signUp()
			is SignUpError -> sendEvent(event)
			is LoginSuccess -> sendEvent(event)
		}
	}

	private fun sendEvent(event: SignUpEvent) {
		viewModelScope.launch { _validationEventChannel.send(event) }
	}

	private fun validateName(name: String) {
		if (_validateUsernameViewState.value.isNeutral() && name.isEmpty()) return
		signUpUseCases.validateNameUseCase(
			params = ValidateNameUseCase.Params(name),
			onSuccess = { onEvent(ValidateNameSuccess(name)) },
			onError = { onEvent(NameError(it)) }
		)
	}

	private fun validateUsername(username: String) {
		if (_validateUsernameViewState.value.isNeutral() && username.isEmpty()) return
		signUpUseCases.validateUsernameUseCase(
			params = ValidateUsernameUseCase.Params(username),
			onSuccess = { onEvent(ValidateUsernameSuccess(username)) },
			onError = { onEvent(UsernameError(it)) }
		)
	}

	private fun validatePassword(password: String) {
		if (_validatePasswordViewState.value.isNeutral() && password.isEmpty()) return
		signUpUseCases.validatePasswordUseCase(
			params = ValidatePasswordUseCase.Params(password),
			onSuccess = { onEvent(ValidatePasswordSuccess(password)) },
			onError = { onEvent(PasswordError(it)) }
		)
	}

	private fun validToLogin() {
		val name = _validateNameViewState.value?.data
		val username = _validateUsernameViewState.value?.data
		val password = _validatePasswordViewState.value?.data
		if (name.isNotNullOrNotEmpty() && username.isNotNullOrNotEmpty() && password.isNotNullOrNotEmpty()) {
			onEvent(ValidToLogin(true))
		} else {
			onEvent(ValidToLogin(false))
		}
	}

	private fun signUp() {
		val name = _validateNameViewState.value?.data.orEmpty()
		val username = _validateUsernameViewState.value?.data.orEmpty()
		val password = _validatePasswordViewState.value?.data.orEmpty()
		signUpUseCases.signUpUseCase(
			params = SignUpUseCase.Params(name, username, password),
			onSuccess = { doLogin() },
			onError = { onEvent(SignUpError(it)) }
		)

	}

	private fun doLogin() {
		val username = _validateUsernameViewState.value?.data.orEmpty()
		val password = _validatePasswordViewState.value?.data.orEmpty()
		signUpUseCases.doLoginUseCase(
			params = DoLoginUseCase.Params(username, password),
			onSuccess = { insertUser(it) },
			onError = { deleteUser(it) }
		)
	}

	private fun insertUser(login: Login) {
		signUpUseCases.insertUserUseCase(
			params = InsertUserUseCase.Params(login),
			onSuccess = { onEvent(LoginSuccess) },
			onError = { deleteUser(it) }
		)
	}

	private fun deleteUser(error: Throwable) {
		signUpUseCases.deleteUserUseCase(
			onSuccess = { onEvent(SignUpError(error)) },
			onError = { onEvent(SignUpError(it)) }
		)
	}
}
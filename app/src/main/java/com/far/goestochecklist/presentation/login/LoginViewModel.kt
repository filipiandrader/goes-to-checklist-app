package com.far.goestochecklist.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.goestochecklist.common.isNotNullOrNotEmpty
import com.far.goestochecklist.common.postSuccess
import com.far.goestochecklist.common.viewState
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.usecase.login.DoLoginUseCase
import com.far.goestochecklist.domain.usecase.login.LoginUseCases
import com.far.goestochecklist.domain.usecase.user.InsertUserUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidatePasswordUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateUsernameUseCase
import com.far.goestochecklist.presentation.core.isNeutral
import com.far.goestochecklist.presentation.login.LoginEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val loginUseCases: LoginUseCases
) : ViewModel() {

	private val _validateUsernameViewState by viewState<String>()
	private val _validatePasswordViewState by viewState<String>()

	private val _validationEventChannel = Channel<LoginEvent>()
	val validationEventChannel = _validationEventChannel.receiveAsFlow()

	fun onEvent(event: LoginEvent) {
		when (event) {
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
			is DoLoginSubmit -> doLogin()
			is LoginSuccess -> sendEvent(event)
			is LoginError -> sendEvent(event)
		}
	}

	private fun sendEvent(event: LoginEvent) {
		viewModelScope.launch { _validationEventChannel.send(event) }
	}

	private fun validateUsername(username: String) {
		if (_validateUsernameViewState.value.isNeutral() && username.isEmpty()) return
		loginUseCases.validateUsernameUseCase(
			params = ValidateUsernameUseCase.Params(username),
			onSuccess = { onEvent(ValidateUsernameSuccess(username)) },
			onError = { onEvent(UsernameError(it)) }
		)
	}

	private fun validatePassword(password: String) {
		if (_validatePasswordViewState.value.isNeutral() && password.isEmpty()) return
		loginUseCases.validatePasswordUseCase(
			params = ValidatePasswordUseCase.Params(password),
			onSuccess = { onEvent(ValidatePasswordSuccess(password)) },
			onError = { onEvent(PasswordError(it)) }
		)
	}

	private fun validToLogin() {
		val username = _validateUsernameViewState.value?.data
		val password = _validatePasswordViewState.value?.data
		if (username.isNotNullOrNotEmpty() && password.isNotNullOrNotEmpty()) {
			onEvent(ValidToLogin(true))
		} else {
			onEvent(ValidToLogin(false))
		}
	}

	private fun doLogin() {
		val username = _validateUsernameViewState.value?.data.orEmpty()
		val password = _validatePasswordViewState.value?.data.orEmpty()
		loginUseCases.doLoginUseCase(
			params = DoLoginUseCase.Params(username, password),
			onSuccess = { insertUser(it) },
			onError = { deleteUser(it) }
		)
	}

	private fun insertUser(login: Login) {
		loginUseCases.insertUserUseCase(
			params = InsertUserUseCase.Params(login),
			onSuccess = { onEvent(LoginSuccess) },
			onError = { deleteUser(it) }
		)
	}

	private fun deleteUser(error: Throwable) {
		loginUseCases.deleteUserUseCase(
			onSuccess = { onEvent(LoginError(error)) },
			onError = { onEvent(LoginError(it)) }
		)
	}
}
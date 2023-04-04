package com.far.goestochecklist.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.goestochecklist.domain.usecase.profile.ProfileUseCases
import com.far.goestochecklist.presentation.profile.ProfileEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 04/04/2023.
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val profileUseCases: ProfileUseCases
) : ViewModel() {

	private val _profileEventChannel = Channel<ProfileEvent>()
	val profileEventChannel = _profileEventChannel.receiveAsFlow()

	fun onEvent(event: ProfileEvent) {
		when (event) {
			is GetUserSubmit -> getUserInfo()
			is GetUserSuccess -> sendEvent(event)
			is GetUserError -> sendEvent(event)
		}
	}

	private fun sendEvent(event: ProfileEvent) {
		viewModelScope.launch { _profileEventChannel.send(event) }
	}

	private fun getUserInfo() {
		profileUseCases.getUserUseCase(
			onSuccess = { onEvent(GetUserSuccess(it!!)) },
			onError = { onEvent(GetUserError(it)) }
		)
	}
}
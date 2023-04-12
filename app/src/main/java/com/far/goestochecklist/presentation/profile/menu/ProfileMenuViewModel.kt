package com.far.goestochecklist.presentation.profile.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.goestochecklist.domain.usecase.profile.ProfileMenuUseCases
import com.far.goestochecklist.presentation.profile.menu.ProfileMenuEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 11/04/2023.
 */

@HiltViewModel
class ProfileMenuViewModel @Inject constructor(
	private val profileMenuUseCases: ProfileMenuUseCases
) : ViewModel() {

	private val _profileMenuEventChannel = Channel<ProfileMenuEvent>()
	val profileMenuEventChannel = _profileMenuEventChannel.receiveAsFlow()

	fun onEvent(event: ProfileMenuEvent) {
		when (event) {
			is LogoutSubmit -> logout()
			is LogoutSuccess -> sendEvent(event)
			is LogoutError -> sendEvent(event)
		}
	}

	private fun sendEvent(event: ProfileMenuEvent) {
		viewModelScope.launch { _profileMenuEventChannel.send(event) }
	}

	private fun logout() {
		profileMenuUseCases.deleteUserUseCase(
			onSuccess = { onEvent(LogoutSuccess) },
			onError = { onEvent(LogoutError(it)) }
		)
	}
}
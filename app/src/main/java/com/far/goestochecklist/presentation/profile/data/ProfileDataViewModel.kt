package com.far.goestochecklist.presentation.profile.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.goestochecklist.domain.usecase.profile.ProfileDataUseCases
import com.far.goestochecklist.presentation.profile.data.ProfileDataEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 04/04/2023.
 */

@HiltViewModel
class ProfileDataViewModel @Inject constructor(
	private val profileDataUseCases: ProfileDataUseCases
) : ViewModel() {

	private val _profileDataEventChannel = Channel<ProfileDataEvent>()
	val profileDataEventChannel = _profileDataEventChannel.receiveAsFlow()

	fun onEvent(event: ProfileDataEvent) {
		when (event) {
			is GetUserSubmit -> getUserInfo()
			is GetUserSuccess -> sendEvent(event)
			is GetUserError -> sendEvent(event)
		}
	}

	private fun sendEvent(event: ProfileDataEvent) {
		viewModelScope.launch { _profileDataEventChannel.send(event) }
	}

	private fun getUserInfo() {
		profileDataUseCases.getUserUseCase(
			onSuccess = { onEvent(GetUserSuccess(it!!)) },
			onError = { onEvent(GetUserError(it)) }
		)
	}
}
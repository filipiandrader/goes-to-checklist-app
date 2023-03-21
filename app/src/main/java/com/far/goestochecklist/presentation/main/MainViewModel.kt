package com.far.goestochecklist.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.goestochecklist.common.asLiveData
import com.far.goestochecklist.common.postSuccess
import com.far.goestochecklist.common.viewState
import com.far.goestochecklist.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
	private val getUserUseCase: GetUserUseCase
) : ViewModel() {

	private val _showSplashScreenViewState by viewState<Boolean>()
	val showSplashScreenViewState = _showSplashScreenViewState.asLiveData()

	private val _mainEventChannel = Channel<MainEvent>()
	val mainEventChannel = _mainEventChannel.receiveAsFlow()

	init {
		onEvent(MainEvent.GetCurrentUser)
	}

	private fun showSplashSplashScreen(isToHide: Boolean = false) {
		_showSplashScreenViewState.postSuccess(isToHide)
	}

	private fun onEvent(event: MainEvent) {
		when (event) {
			MainEvent.GetCurrentUser -> getUser()
			else -> viewModelScope.launch {
				showSplashSplashScreen(true)
				_mainEventChannel.send(event)
			}
		}
	}

	private fun getUser() {
		getUserUseCase(
			onSuccess = {
				if (it != null) {
					onEvent(MainEvent.UserLogged)
				} else {
					onEvent(MainEvent.UserNotLogged)
				}
			},
			onError = { onEvent(MainEvent.UserNotLogged) }
		)
	}
}

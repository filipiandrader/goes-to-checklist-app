package com.far.goestochecklist.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.goestochecklist.domain.usecase.film.GetFilmUseCase
import com.far.goestochecklist.domain.usecase.home.HomeUseCases
import com.far.goestochecklist.domain.usecase.user.MarkWatchUseCase
import com.far.goestochecklist.presentation.home.HomeEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 23/03/2023.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val homeUseCases: HomeUseCases
) : ViewModel() {

	private val _homeEventChannel = Channel<HomeEvent>()
	val homeEventChannel = _homeEventChannel.receiveAsFlow()

	fun onEvent(event: HomeEvent) {
		when (event) {
			is GetUserSubmit -> getUserInfo()
			is GetUserSuccess -> sendEvent(event)
			is GetUserError -> sendEvent(event)
			is GetYearSubmit -> getYear()
			is GetYearSuccess -> sendEvent(event)
			is GetYearError -> sendEvent(event)
			is GetFilmSubmit -> getFilm(event.year)
			is GetFilmSuccess -> sendEvent(event)
			is GetFilmError -> sendEvent(event)
			is MarkWatchSubmit -> markWatch(event.filmId)
			is MarkWatchSuccess -> sendEvent(event)
			is MarkWatchError -> sendEvent(event)
		}
	}

	private fun sendEvent(event: HomeEvent) {
		viewModelScope.launch { _homeEventChannel.send(event) }
	}

	private fun getUserInfo() {
		homeUseCases.getUserUseCase(
			onSuccess = { onEvent(GetUserSuccess(it!!)) },
			onError = { onEvent(GetUserError(it)) }
		)
	}

	private fun getYear() {
		homeUseCases.getYearUseCase(
			onSuccess = { onEvent(GetYearSuccess(it)) },
			onError = { onEvent(GetYearError(it)) }
		)
	}

	private fun getFilm(year: String) {
		homeUseCases.getFilmUseCase(
			params = GetFilmUseCase.Params(year),
			onSuccess = { onEvent(GetFilmSuccess(it)) },
			onError = { onEvent(GetFilmError(it)) }
		)
	}

	private fun markWatch(filmId: String) {
		homeUseCases.markWatchUseCase(
			params = MarkWatchUseCase.Params(filmId),
			onSuccess = { onEvent(MarkWatchSuccess) },
			onError = { onEvent(MarkWatchError(it)) }
		)
	}
}
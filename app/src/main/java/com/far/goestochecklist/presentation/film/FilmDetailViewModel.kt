package com.far.goestochecklist.presentation.film

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.goestochecklist.domain.usecase.film.FilmDetailUseCases
import com.far.goestochecklist.domain.usecase.user.MarkWatchUseCase
import com.far.goestochecklist.presentation.film.FilmDetailEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 31/03/2023.
 */

@HiltViewModel
class FilmDetailViewModel @Inject constructor(
	private val filmDetailUseCases: FilmDetailUseCases
) : ViewModel() {

	private val _filmDetailEventChannel = Channel<FilmDetailEvent>()
	val filmDetailEventChannel = _filmDetailEventChannel.receiveAsFlow()

	fun onEvent(event: FilmDetailEvent) {
		when (event) {
			is MarkWatchSubmit -> markWatch(event.filmId)
			is MarkWatchSuccess -> sendEvent(event)
			is MarkWatchError -> sendEvent(event)
		}
	}

	private fun sendEvent(event: FilmDetailEvent) {
		viewModelScope.launch { _filmDetailEventChannel.send(event) }
	}

	private fun markWatch(filmId: String) {
		filmDetailUseCases.markWatchUseCase(
			params = MarkWatchUseCase.Params(filmId),
			onSuccess = { onEvent(MarkWatchSuccess) },
			onError = { onEvent(MarkWatchError(it)) }
		)
	}
}
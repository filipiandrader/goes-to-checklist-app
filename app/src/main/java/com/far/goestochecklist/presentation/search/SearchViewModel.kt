package com.far.goestochecklist.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.goestochecklist.domain.usecase.film.GetFilmByFiltersUseCase
import com.far.goestochecklist.domain.usecase.search.SearchUseCases
import com.far.goestochecklist.domain.usecase.user.MarkWatchUseCase
import com.far.goestochecklist.presentation.search.SearchEvent.GetFilmByFiltersError
import com.far.goestochecklist.presentation.search.SearchEvent.GetFilmByFiltersSubmit
import com.far.goestochecklist.presentation.search.SearchEvent.GetFilmByFiltersSuccess
import com.far.goestochecklist.presentation.search.SearchEvent.GetFiltersError
import com.far.goestochecklist.presentation.search.SearchEvent.GetFiltersSubmit
import com.far.goestochecklist.presentation.search.SearchEvent.GetFiltersSuccess
import com.far.goestochecklist.presentation.search.SearchEvent.MarkWatchError
import com.far.goestochecklist.presentation.search.SearchEvent.MarkWatchSubmit
import com.far.goestochecklist.presentation.search.SearchEvent.MarkWatchSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 18/04/2023.
 */

@HiltViewModel
class SearchViewModel @Inject constructor(
	private val searchUseCases: SearchUseCases
) : ViewModel() {

	private val _searchEventChannel = Channel<SearchEvent>()
	val searchEventChannel = _searchEventChannel.receiveAsFlow()

	fun onEvent(event: SearchEvent) {
		when (event) {
			is GetFiltersSubmit -> getFilters()
			is GetFiltersSuccess -> sendEvent(event)
			is GetFiltersError -> sendEvent(event)
			is GetFilmByFiltersSubmit -> getFilmByFilters(
				event.categoryName, event.year, event.filmName
			)

			is GetFilmByFiltersSuccess -> sendEvent(event)
			is GetFilmByFiltersError -> sendEvent(event)
			is MarkWatchSubmit -> markWatch(event.filmId)
			is MarkWatchSuccess -> sendEvent(event)
			is MarkWatchError -> sendEvent(event)
		}
	}

	private fun sendEvent(event: SearchEvent) {
		viewModelScope.launch { _searchEventChannel.send(event) }
	}

	private fun getFilters() {
		searchUseCases.getFiltersUseCase(
			onSuccess = { onEvent(GetFiltersSuccess(it)) },
			onError = { onEvent(GetFiltersError(it)) }
		)
	}

	private fun getFilmByFilters(categoryName: String, year: String, filmName: String) {
		searchUseCases.getFilmByFiltersUseCase(
			params = GetFilmByFiltersUseCase.Params(categoryName, year, filmName),
			onSuccess = { onEvent(GetFilmByFiltersSuccess(it.sortedBy { film -> film.id })) },
			onError = { onEvent(GetFilmByFiltersError(it)) }
		)
	}

	private fun markWatch(filmId: String) {
		searchUseCases.markWatchUseCase(
			params = MarkWatchUseCase.Params(filmId),
			onSuccess = { onEvent(MarkWatchSuccess(filmId)) },
			onError = { onEvent(MarkWatchError(it)) }
		)
	}
}
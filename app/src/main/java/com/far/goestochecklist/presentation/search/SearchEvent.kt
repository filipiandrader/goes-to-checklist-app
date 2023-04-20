package com.far.goestochecklist.presentation.search

import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.domain.model.Filter

/*
 * Created by Filipi Andrade Rocha on 18/04/2023.
 */

sealed class SearchEvent {
	object GetFiltersSubmit : SearchEvent()
	data class GetFiltersSuccess(val filters: Filter) : SearchEvent()
	data class GetFiltersError(val throwable: Throwable) : SearchEvent()
	data class GetFilmByFiltersSubmit(
		var categoryName: String, var year: String, var filmName: String
	) : SearchEvent()

	data class GetFilmByFiltersSuccess(val films: List<Film>) : SearchEvent()
	data class GetFilmByFiltersError(val throwable: Throwable) : SearchEvent()
	data class MarkWatchSubmit(val filmId: String) : SearchEvent()
	data class MarkWatchSuccess(val filmId: String) : SearchEvent()
	data class MarkWatchError(val throwable: Throwable) : SearchEvent()
}

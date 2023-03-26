package com.far.goestochecklist.presentation.home

import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.model.Year

/*
 * Created by Filipi Andrade Rocha on 23/03/2023.
 */

sealed class HomeEvent {
	object GetUserSubmit : HomeEvent()
	data class GetUserSuccess(val user: Login) : HomeEvent()
	data class GetUserError(val throwable: Throwable) : HomeEvent()
	object GetYearSubmit : HomeEvent()
	data class GetYearSuccess(val years: List<Year>) : HomeEvent()
	data class GetYearError(val throwable: Throwable) : HomeEvent()
	data class GetFilmSubmit(val year: String) : HomeEvent()
	data class GetFilmSuccess(val films: List<Film>) : HomeEvent()
	data class GetFilmError(val throwable: Throwable) : HomeEvent()
	data class MarkWatchSubmit(val filmId: String) : HomeEvent()
	data class MarkWatchSuccess(val filmId: String) : HomeEvent()
	data class MarkWatchError(val throwable: Throwable) : HomeEvent()
}

package com.far.goestochecklist.presentation.film

/*
 * Created by Filipi Andrade Rocha on 31/03/2023.
 */

sealed class FilmDetailEvent {
	data class MarkWatchSubmit(val filmId: String) : FilmDetailEvent()
	object MarkWatchSuccess : FilmDetailEvent()
	data class MarkWatchError(val throwable: Throwable) : FilmDetailEvent()
}

package com.far.goestochecklist.domain.usecase.home

import com.far.goestochecklist.domain.usecase.film.GetFilmUseCase
import com.far.goestochecklist.domain.usecase.user.GetUserUseCase
import com.far.goestochecklist.domain.usecase.user.MarkWatchUseCase
import com.far.goestochecklist.domain.usecase.year.GetYearUseCase

/*
 * Created by Filipi Andrade Rocha on 23/03/2023.
 */

data class HomeUseCases(
	val getUserUseCase: GetUserUseCase,
	val getYearUseCase: GetYearUseCase,
	val getFilmUseCase: GetFilmUseCase,
	val markWatchUseCase: MarkWatchUseCase
)

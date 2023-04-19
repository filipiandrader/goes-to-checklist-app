package com.far.goestochecklist.domain.usecase.search

import com.far.goestochecklist.domain.usecase.film.GetFilmByFiltersUseCase
import com.far.goestochecklist.domain.usecase.filter.GetFiltersUseCase

/*
 * Created by Filipi Andrade Rocha on 18/04/2023.
 */

data class SearchUseCases(
	val getFiltersUseCase: GetFiltersUseCase,
	val getFilmByFiltersUseCase: GetFilmByFiltersUseCase,
)

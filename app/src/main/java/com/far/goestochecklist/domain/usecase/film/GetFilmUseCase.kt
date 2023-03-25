package com.far.goestochecklist.domain.usecase.film

import com.far.goestochecklist.domain.core.UseCase
import com.far.goestochecklist.domain.exception.EmptyParamException
import com.far.goestochecklist.domain.exception.MissingParamsException
import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 23/03/2023.
 */

class GetFilmUseCase @Inject constructor(private val repository: GoesToChecklistRepository) :
	UseCase<List<Film>, GetFilmUseCase.Params>() {

	override fun run(params: Params?) = when {
		params == null -> throw MissingParamsException()
		params.year.isEmpty() -> throw EmptyParamException()
		else -> repository.getFilms(params.year)
	}

	data class Params(val year: String)
}
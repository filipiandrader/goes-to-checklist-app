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

class GetFilmByIdUseCase @Inject constructor(private val repository: GoesToChecklistRepository) :
	UseCase<Film, GetFilmByIdUseCase.Params>() {

	override fun run(params: Params?) = when {
		params == null -> throw MissingParamsException()
		params.year.isEmpty() -> throw EmptyParamException()
		params.userId.isEmpty() -> throw EmptyParamException()
		params.filmId.isEmpty() -> throw EmptyParamException()
		else -> repository.getFilmById(params.year, params.userId, params.filmId)
	}

	data class Params(val year: String, val userId: String, val filmId: String)
}
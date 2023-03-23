package com.far.goestochecklist.domain.usecase.user

import com.far.goestochecklist.domain.core.UseCase
import com.far.goestochecklist.domain.exception.EmptyParamException
import com.far.goestochecklist.domain.exception.MissingParamsException
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 23/03/2023.
 */

class MarkWatchUseCase @Inject constructor(private val repository: GoesToChecklistRepository) :
	UseCase<Unit, MarkWatchUseCase.Params>() {

	override fun run(params: Params?) = when {
		params == null -> throw MissingParamsException()
		params.filmId.isEmpty() -> throw EmptyParamException()
		params.userId.isEmpty() -> throw EmptyParamException()
		else -> repository.markWatch(params.filmId, params.userId)
	}

	data class Params(val filmId: String, val userId: String)
}
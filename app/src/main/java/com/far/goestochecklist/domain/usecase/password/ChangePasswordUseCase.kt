package com.far.goestochecklist.domain.usecase.password

import com.far.goestochecklist.domain.core.UseCase
import com.far.goestochecklist.domain.exception.EmptyParamException
import com.far.goestochecklist.domain.exception.MissingParamsException
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 22/04/2023.
 */

class ChangePasswordUseCase @Inject constructor(private val repository: GoesToChecklistRepository) :
	UseCase<Unit, ChangePasswordUseCase.Params>() {

	override fun run(params: Params?) = when {
		params == null -> throw MissingParamsException()
		params.password.isEmpty() -> throw EmptyParamException()
		else -> repository.changePassword(params.password)
	}

	data class Params(val password: String)
}
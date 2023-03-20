package com.far.goestochecklist.domain.usecase.signup

import com.far.goestochecklist.domain.exception.EmptyParamException
import com.far.goestochecklist.domain.exception.MissingParamsException
import com.far.goestochecklist.domain.core.UseCase
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

class SignUpUseCase @Inject constructor(private val repository: GoesToChecklistRepository) :
	UseCase<Unit, SignUpUseCase.Params>() {

	override fun run(params: Params?) = when {
		params == null -> throw MissingParamsException()
		params.name.isEmpty() -> throw EmptyParamException()
		params.username.isEmpty() -> throw EmptyParamException()
		params.password.isEmpty() -> throw EmptyParamException()
		else -> repository.signUp(params.name, params.username, params.password)
	}

	data class Params(val name: String, val username: String, val password: String)
}
package com.far.goestochecklist.domain.usecase.login

import com.far.goestochecklist.domain.exception.EmptyParamException
import com.far.goestochecklist.domain.exception.MissingParamsException
import com.far.goestochecklist.domain.core.UseCase
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

class DoLoginUseCase @Inject constructor(private val repository: GoesToChecklistRepository) :
	UseCase<Login, DoLoginUseCase.Params>() {

	override fun run(params: Params?) = when {
		params == null -> throw MissingParamsException()
		params.username.isEmpty() -> throw EmptyParamException()
		params.password.isEmpty() -> throw EmptyParamException()
		else -> repository.login(params.username, params.password)
	}

	data class Params(val username: String, val password: String)
}
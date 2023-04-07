package com.far.goestochecklist.domain.usecase.user

import com.far.goestochecklist.domain.core.UseCase
import com.far.goestochecklist.domain.exception.MissingParamsException
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 07/04/2023.
 */

class UpdateUserInfoLocallyUseCase @Inject constructor(private val repository: GoesToChecklistRepository) :
	UseCase<Unit, UpdateUserInfoLocallyUseCase.Params>() {

	override fun run(params: Params?) = when (params == null) {
		true -> throw MissingParamsException()
		false -> repository.updateUserInfoLocally(params.login)
	}

	data class Params(val login: Login)
}
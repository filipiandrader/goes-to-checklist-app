package com.far.goestochecklist.domain.usecase.validators

import com.far.goestochecklist.domain.core.UseCase
import com.far.goestochecklist.domain.exception.EmptyParamException
import com.far.goestochecklist.domain.exception.MissingParamsException
import com.far.goestochecklist.domain.exception.PasswordsMismatchException
import kotlinx.coroutines.flow.flowOf

class ValidateChangePasswordUseCase : UseCase<Unit, ValidateChangePasswordUseCase.Params>() {

	override fun run(params: Params?) = when {
		params == null -> throw MissingParamsException()
		params.newPassword.trim().isEmpty() -> throw EmptyParamException()
		params.confirmNewPassword.trim().isEmpty() -> throw EmptyParamException()
		params.newPassword != params.confirmNewPassword -> throw PasswordsMismatchException()
		else -> flowOf(Unit)
	}

	data class Params(val newPassword: String, val confirmNewPassword: String)
}
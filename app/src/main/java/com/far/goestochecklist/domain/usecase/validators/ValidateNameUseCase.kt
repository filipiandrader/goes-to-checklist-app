package com.far.goestochecklist.domain.usecase.validators

import com.far.goestochecklist.domain.exception.EmptyParamException
import com.far.goestochecklist.domain.exception.MissingParamsException
import com.far.goestochecklist.domain.core.UseCase
import kotlinx.coroutines.flow.flowOf

class ValidateNameUseCase : UseCase<Unit, ValidateNameUseCase.Params>() {

    override fun run(params: Params?) = when {
        params == null -> throw MissingParamsException()
        params.name.trim().isEmpty() -> throw EmptyParamException()
        else -> flowOf(Unit)
    }

    data class Params(val name: String)
}
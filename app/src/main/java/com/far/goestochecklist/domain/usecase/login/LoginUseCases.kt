package com.far.goestochecklist.domain.usecase.login

import com.far.goestochecklist.domain.usecase.validators.ValidatePasswordUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateUsernameUseCase

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

data class LoginUseCases(
	val doLoginUseCase: DoLoginUseCase,
	val validateUsernameUseCase: ValidateUsernameUseCase,
	val validatePasswordUseCase: ValidatePasswordUseCase
)

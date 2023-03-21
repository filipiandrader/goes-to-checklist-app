package com.far.goestochecklist.domain.usecase.signup

import com.far.goestochecklist.domain.usecase.login.DoLoginUseCase
import com.far.goestochecklist.domain.usecase.user.DeleteUserUseCase
import com.far.goestochecklist.domain.usecase.user.InsertUserUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateNameUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidatePasswordUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateUsernameUseCase

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

data class SignUpUseCases(
	val signUpUseCase: SignUpUseCase,
	val validateNameUseCase: ValidateNameUseCase,
	val validateUsernameUseCase: ValidateUsernameUseCase,
	val validatePasswordUseCase: ValidatePasswordUseCase,
	val doLoginUseCase: DoLoginUseCase,
	val insertUserUseCase: InsertUserUseCase,
	val deleteUserUseCase: DeleteUserUseCase
)

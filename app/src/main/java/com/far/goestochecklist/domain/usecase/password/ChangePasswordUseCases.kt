package com.far.goestochecklist.domain.usecase.password

import com.far.goestochecklist.domain.usecase.validators.ValidateChangePasswordUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateConfirmNewPasswordUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateNewPasswordUseCase

/*
 * Created by Filipi Andrade Rocha on 22/04/2023.
 */

data class ChangePasswordUseCases(
	val changePasswordUseCase: ChangePasswordUseCase,
	val validateNewPasswordUseCase: ValidateNewPasswordUseCase,
	val validateConfirmNewPasswordUseCase: ValidateConfirmNewPasswordUseCase,
	val validateChangePasswordUseCase: ValidateChangePasswordUseCase
)

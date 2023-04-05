package com.far.goestochecklist.domain.usecase.profile

import com.far.goestochecklist.domain.usecase.user.InsertUserUseCase
import com.far.goestochecklist.domain.usecase.user.UpdateUserInfoUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateNameUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateUsernameUseCase

/*
 * Created by Filipi Andrade Rocha on 04/04/2023.
 */

data class EditProfileDataUseCases(
	val validateNameUseCase: ValidateNameUseCase,
	val validateUsernameUseCase: ValidateUsernameUseCase,
	val updateUserInfoUseCase: UpdateUserInfoUseCase,
	val insertUserUseCase: InsertUserUseCase
)

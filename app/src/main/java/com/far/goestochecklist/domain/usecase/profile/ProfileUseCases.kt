package com.far.goestochecklist.domain.usecase.profile

import com.far.goestochecklist.domain.usecase.user.DeleteUserUseCase
import com.far.goestochecklist.domain.usecase.user.GetUserUseCase

/*
 * Created by Filipi Andrade Rocha on 04/04/2023.
 */

data class ProfileUseCases(
	val getUserUseCase: GetUserUseCase,
	val deleteUserUseCase: DeleteUserUseCase
)

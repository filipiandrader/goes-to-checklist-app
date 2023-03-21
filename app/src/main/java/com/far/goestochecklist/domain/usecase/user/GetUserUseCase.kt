package com.far.goestochecklist.domain.usecase.user

import com.far.goestochecklist.domain.core.UseCase
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 20/03/2023.
 */

class GetUserUseCase @Inject constructor(private val repository: GoesToChecklistRepository) :
	UseCase<Login?, Unit>() {

	override fun run(params: Unit?) = repository.getUser()
}
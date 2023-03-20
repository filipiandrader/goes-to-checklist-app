package com.far.goestochecklist.data.mapper

import com.far.goestochecklist.data.remote.dto.LoginResponse
import com.far.goestochecklist.domain.model.Login

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

object LoginMapper : DataRemoteMapper<LoginResponse, Login> {

	override fun toDomain(data: LoginResponse) = Login(
		userId = data.userId.orEmpty(),
		name = data.name.orEmpty(),
		username = data.username.orEmpty()
	)
}
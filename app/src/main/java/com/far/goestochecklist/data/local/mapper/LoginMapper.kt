package com.far.goestochecklist.data.local.mapper

import com.far.goestochecklist.data.local.entity.LoginEntity
import com.far.goestochecklist.domain.model.Login

/*
 * Created by Filipi Andrade Rocha on 20/03/2023.
 */

object LoginMapper : DataLocalMapper<LoginEntity?, Login> {

	override fun toLocal(domain: Login) = LoginEntity(
		userId = domain.userId,
		name = domain.name,
		username = domain.username
	)

	override fun fromLocal(local: LoginEntity?) = Login(
		userId = local?.userId.orEmpty(),
		name = local?.name.orEmpty(),
		username = local?.username.orEmpty()
	)
}
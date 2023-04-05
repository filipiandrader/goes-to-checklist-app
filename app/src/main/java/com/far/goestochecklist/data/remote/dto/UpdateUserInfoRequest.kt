package com.far.goestochecklist.data.remote.dto

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

data class UpdateUserInfoRequest(
	val userId: String,
	val name: String,
	val username: String
)

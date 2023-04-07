package com.far.goestochecklist.data.remote.dto

import com.google.gson.annotations.SerializedName

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

data class UpdateUserInfoRequest(
	@SerializedName("user_id") val userId: String,
	val name: String,
	val username: String
)

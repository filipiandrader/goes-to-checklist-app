package com.far.goestochecklist.data.remote.dto

import com.google.gson.annotations.SerializedName

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

data class LoginResponse(
	@SerializedName("user_id") var userId: String? = null,
	var name: String? = null,
	var username: String? = null
)

package com.far.goestochecklist.data.remote.dto

/*
 * Created by Filipi Andrade Rocha on 22/03/2023.
 */

data class GenericResponse<T>(
	val message: MessageResponse? = null,
	val data: T?
)

data class MessageResponse(
	val code: String? = null,
	val message: String? = null
)

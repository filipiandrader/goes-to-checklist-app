package com.far.goestochecklist.data.remote.service

import com.far.goestochecklist.data.remote.dto.GenericResponse
import com.far.goestochecklist.data.remote.dto.LoginRequest
import com.far.goestochecklist.data.remote.dto.LoginResponse
import com.far.goestochecklist.data.remote.dto.UserRequest
import retrofit2.http.Body
import retrofit2.http.POST

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

interface GoesToChecklistService {

	@POST("/user")
	suspend fun signUp(@Body userRequest: UserRequest): GenericResponse<Unit>

	@POST("/login")
	suspend fun login(@Body loginRequest: LoginRequest): GenericResponse<LoginResponse>
}
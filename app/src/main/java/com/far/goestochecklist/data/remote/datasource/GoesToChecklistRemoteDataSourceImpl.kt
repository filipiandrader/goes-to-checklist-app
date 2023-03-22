package com.far.goestochecklist.data.remote.datasource

import com.far.goestochecklist.data.datasource.remote.GoesToChecklistRemoteDataSource
import com.far.goestochecklist.data.remote.dto.LoginRequest
import com.far.goestochecklist.data.remote.dto.UserRequest
import com.far.goestochecklist.data.remote.mapper.login.LoginMapper
import com.far.goestochecklist.data.remote.service.GoesToChecklistService
import com.far.goestochecklist.data.remote.utils.RequestWrapper
import kotlinx.coroutines.flow.flow

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

class GoesToChecklistRemoteDataSourceImpl(
	private val service: GoesToChecklistService,
	private val wrapper: RequestWrapper
) : GoesToChecklistRemoteDataSource {

	override fun signUp(name: String, username: String, password: String) = flow {
		emit(
			wrapper.wrapper {
				service.signUp(UserRequest(name, username, password))
			}.data!!
		)
	}

	override fun login(username: String, password: String) = flow {
		emit(
			LoginMapper.toDomain(
				wrapper.wrapper {
					service.login(LoginRequest(username, password))
				}.data!!
			)
		)
	}
}
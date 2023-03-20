package com.far.goestochecklist.data.repository

import com.far.goestochecklist.data.datasource.GoesToChecklistRemoteDataSource
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

class GoesToChecklistRepositoryImpl @Inject constructor(
	private val remoteDataSource: GoesToChecklistRemoteDataSource
) : GoesToChecklistRepository {

	override fun signUp(name: String, username: String, password: String) =
		remoteDataSource.signUp(name, username, password)

	override fun login(username: String, password: String) =
		remoteDataSource.login(username, password)
}
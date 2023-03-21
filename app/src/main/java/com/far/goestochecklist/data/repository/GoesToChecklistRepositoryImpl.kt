package com.far.goestochecklist.data.repository

import com.far.goestochecklist.data.datasource.local.GoesToChecklistLocalDataSource
import com.far.goestochecklist.data.datasource.remote.GoesToChecklistRemoteDataSource
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

class GoesToChecklistRepositoryImpl @Inject constructor(
	private val remoteDataSource: GoesToChecklistRemoteDataSource,
	private val localDataSource: GoesToChecklistLocalDataSource
) : GoesToChecklistRepository {

	override fun signUp(name: String, username: String, password: String) =
		remoteDataSource.signUp(name, username, password)

	override fun login(username: String, password: String) =
		remoteDataSource.login(username, password)

	override fun insertUser(login: Login) = flow { emit(localDataSource.insertUser(login)) }

	override fun deleteUser() = flow { emit(localDataSource.deleteUser()) }

	override fun getUser() = flow { emit(localDataSource.getUser()) }
}
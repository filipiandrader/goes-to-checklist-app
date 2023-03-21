package com.far.goestochecklist.data.datasource.remote

import com.far.goestochecklist.domain.model.Login
import kotlinx.coroutines.flow.Flow

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

interface GoesToChecklistRemoteDataSource {

	fun signUp(name: String, username: String, password: String): Flow<Unit>
	fun login(username: String, password: String): Flow<Login>
}
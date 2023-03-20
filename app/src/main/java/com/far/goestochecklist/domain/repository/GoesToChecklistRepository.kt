package com.far.goestochecklist.domain.repository

import com.far.goestochecklist.domain.model.Login
import kotlinx.coroutines.flow.Flow

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

interface GoesToChecklistRepository {

	fun signUp(name: String, username: String, password: String): Flow<Unit>
	fun login(username: String, password: String): Flow<Login>
}
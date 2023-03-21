package com.far.goestochecklist.data.datasource.local

import com.far.goestochecklist.domain.model.Login

/*
 * Created by Filipi Andrade Rocha on 20/03/2023.
 */

interface GoesToChecklistLocalDataSource {

	suspend fun insertUser(login: Login)
	suspend fun deleteUser()
	suspend fun getUser(): Login?
}
package com.far.goestochecklist.data.local.datasource

import com.far.goestochecklist.data.datasource.local.GoesToChecklistLocalDataSource
import com.far.goestochecklist.data.local.database.GoesToChecklistDatabase
import com.far.goestochecklist.data.local.mapper.LoginMapper
import com.far.goestochecklist.domain.model.Login

/*
 * Created by Filipi Andrade Rocha on 20/03/2023.
 */

class GoesToChecklistLocalDataSourceImpl(
	private val db: GoesToChecklistDatabase
) : GoesToChecklistLocalDataSource {

	override suspend fun insertUser(login: Login) =
		db.userDao.insertUser(LoginMapper.toLocal(login))

	override suspend fun deleteUser() = db.userDao.deleteUser()

	override suspend fun getUser(): Login? {
		return if (db.userDao.getUser() != null) {
			LoginMapper.fromLocal(db.userDao.getUser())
		} else {
			null
		}
	}
}
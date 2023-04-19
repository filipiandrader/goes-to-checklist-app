package com.far.goestochecklist.data.repository

import com.far.goestochecklist.data.datasource.local.GoesToChecklistLocalDataSource
import com.far.goestochecklist.data.datasource.remote.GoesToChecklistRemoteDataSource
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
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

	override fun getYear() = remoteDataSource.getYear()

	override fun getFilms(year: String) = flow {
		val user = getUser().single()
		emit(
			remoteDataSource.getFilms(year, user?.userId.orEmpty()).single()
		)
	}

	override fun getFilmById(year: String, filmId: String) = flow {
		val user = getUser().single()
		emit(
			remoteDataSource.getFilmById(year, user?.userId.orEmpty(), filmId).single()
		)
	}

	override fun getFilters() = remoteDataSource.getFilters()

	override fun getFilmByFilters(categoryName: String, year: String, filmName: String) = flow {
		val user = getUser().single()
		emit(
			remoteDataSource.getFilmByFilters(
				user?.userId.orEmpty(), categoryName, year, filmName
			).single()
		)
	}

	override fun markWatch(filmId: String) = flow {
		val user = getUser().single()
		emit(
			remoteDataSource.markWatch(filmId, user?.userId.orEmpty()).single()
		)
	}

	override fun updateUserInfo(login: Login) =
		remoteDataSource.updateUserInfo(login.userId, login.name, login.username)

	override fun insertUser(login: Login) = flow { emit(localDataSource.insertUser(login)) }

	override fun deleteUser() = flow { emit(localDataSource.deleteUser()) }

	override fun getUser() = flow { emit(localDataSource.getUser()) }

	override fun updateUserInfoLocally(login: Login) = flow {
		emit(localDataSource.updateUserInfo(login.userId, login.name, login.username))
	}
}
package com.far.goestochecklist.data.remote.datasource

import com.far.goestochecklist.data.datasource.remote.GoesToChecklistRemoteDataSource
import com.far.goestochecklist.data.remote.dto.LoginRequest
import com.far.goestochecklist.data.remote.dto.MarkWatchRequest
import com.far.goestochecklist.data.remote.dto.UpdateUserInfoRequest
import com.far.goestochecklist.data.remote.dto.UserRequest
import com.far.goestochecklist.data.remote.mapper.film.FilmMapper
import com.far.goestochecklist.data.remote.mapper.login.LoginMapper
import com.far.goestochecklist.data.remote.mapper.year.YearMapper
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

	override fun getYear() = flow {
		emit(
			YearMapper.toDomain(
				wrapper.wrapper {
					service.getYear()
				}.data!!
			)
		)
	}

	override fun getFilms(year: String, userId: String) = flow {
		emit(
			FilmMapper.toDomain(
				wrapper.wrapper {
					service.getFilms(year, userId)
				}.data!!
			)
		)
	}

	override fun getFilmById(year: String, userId: String, filmId: String) = flow {
		emit(
			FilmMapper.toDomain(
				wrapper.wrapper {
					service.getFilmById(year, userId, filmId)
				}.data!!
			)
		)
	}

	override fun markWatch(filmId: String, userId: String) = flow {
		emit(
			wrapper.wrapper {
				service.markWatch(MarkWatchRequest(filmId, userId))
			}.data!!
		)
	}

	override fun updateUserInfo(userId: String, name: String, username: String) = flow {
		emit(
			wrapper.wrapper {
				service.updateUserInfo(UpdateUserInfoRequest(userId, name, username))
			}.data!!
		)
	}
}
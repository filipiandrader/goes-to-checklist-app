package com.far.goestochecklist.data.datasource.remote

import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.model.Year
import kotlinx.coroutines.flow.Flow

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

interface GoesToChecklistRemoteDataSource {

	fun signUp(name: String, username: String, password: String): Flow<Unit>
	fun login(username: String, password: String): Flow<Login>
	fun getYear(): Flow<List<Year>>
	fun getFilms(year: String, userId: String): Flow<List<Film>>
	fun getFilmById(year: String, userId: String, filmId: String): Flow<Film>
	fun markWatch(filmId: String, userId: String): Flow<Unit>
	fun updateUserInfo(userId: String, name: String, username: String): Flow<Unit>
}
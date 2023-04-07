package com.far.goestochecklist.domain.repository

import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.model.Year
import kotlinx.coroutines.flow.Flow

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

interface GoesToChecklistRepository {

	fun signUp(name: String, username: String, password: String): Flow<Unit>
	fun login(username: String, password: String): Flow<Login>
	fun getYear(): Flow<List<Year>>
	fun getFilms(year: String): Flow<List<Film>>
	fun getFilmById(year: String, filmId: String): Flow<Film>
	fun markWatch(filmId: String): Flow<Unit>
	fun updateUserInfo(login: Login): Flow<Unit>
	fun insertUser(login: Login): Flow<Unit>
	fun deleteUser(): Flow<Unit>
	fun getUser(): Flow<Login?>
	fun updateUserInfoLocally(login: Login): Flow<Unit>
}
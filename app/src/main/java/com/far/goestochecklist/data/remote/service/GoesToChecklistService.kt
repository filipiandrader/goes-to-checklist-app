package com.far.goestochecklist.data.remote.service

import com.far.goestochecklist.data.remote.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

interface GoesToChecklistService {

	@POST("/user")
	suspend fun signUp(@Body userRequest: UserRequest): GenericResponse<Unit>

	@POST("/login")
	suspend fun login(@Body loginRequest: LoginRequest): GenericResponse<LoginResponse>

	@GET("/year")
	suspend fun getYear(): GenericResponse<List<YearResponse>>

	@GET("/film/{year}/{user_id}")
	suspend fun getFilms(
		@Path("year") year: String,
		@Path("user_id") userId: String
	): GenericResponse<List<FilmResponse>>

	@GET("/film/{year}/{user_id}/{film_id}")
	suspend fun getFilmById(
		@Path("year") year: String,
		@Path("user_id") userId: String,
		@Path("film_id") filmId: String,
	): GenericResponse<FilmResponse>

	@GET("/filter")
	suspend fun getFilters(): GenericResponse<FilterResponse>

	@GET("/filter/{user_id}")
	suspend fun getFilmByFilters(
		@Path("user_id") userId: String,
		@Query("category_name") categoryName: String = "",
		@Query("year") year: String = "",
		@Query("film_name") filmName: String = ""
	): GenericResponse<List<FilmResponse>>

	@POST("/user/watch")
	suspend fun markWatch(@Body markWatchRequest: MarkWatchRequest): GenericResponse<Unit>

	@PUT("/user/update")
	suspend fun updateUserInfo(@Body updateUserInfoRequest: UpdateUserInfoRequest): GenericResponse<Unit>

	@PUT("/user/update/password")
	suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): GenericResponse<Unit>
}
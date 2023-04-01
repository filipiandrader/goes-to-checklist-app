package com.far.goestochecklist.data.remote.utils

import com.far.goestochecklist.common.Constants
import com.far.goestochecklist.common.Constants.GENERIC_ERROR
import com.far.goestochecklist.common.fromJson
import com.far.goestochecklist.data.remote.dto.GenericResponse
import com.far.goestochecklist.domain.exception.DataSourceException
import retrofit2.HttpException
import java.net.SocketTimeoutException

class RequestWrapperImpl : RequestWrapper {

	override suspend fun <T> wrapper(call: suspend () -> GenericResponse<T>): GenericResponse<T> {
		return try {
			call()
		} catch (httpException: HttpException) {
			throw handleException(httpException)
		} catch (exception: Exception) {
			throw Exception(GENERIC_ERROR)
		}
	}

	private fun handleException(httpException: HttpException): DataSourceException {
		val json = httpException.response()?.errorBody()?.string().orEmpty()
		val error = json.fromJson<GenericResponse<*>>()
		val messageResponse = error?.message
		val message = messageResponse?.message
		val code = messageResponse?.code
		return DataSourceException(
			message = message ?: GENERIC_ERROR,
			code = code.orEmpty(),
			cause = httpException
		)
	}
}
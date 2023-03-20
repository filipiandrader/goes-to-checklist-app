package com.far.goestochecklist.data.remote.utils

import com.far.goestochecklist.domain.exception.DataSourceException
import com.far.goestochecklist.common.Constants
import retrofit2.HttpException

class RequestWrapperImpl : RequestWrapper {

	override suspend fun <T> wrapper(call: suspend () -> T): T {
		return try {
			call()
		} catch (httpException: HttpException) {
			throw handleException(httpException)
		} catch (exception: Exception) {
			throw exception
		}
	}

	private fun handleException(httpException: HttpException): DataSourceException {
		return DataSourceException(
			message = Constants.GENERIC_ERROR,
			cause = httpException
		)
	}
}
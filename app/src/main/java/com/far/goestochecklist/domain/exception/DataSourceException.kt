package com.far.goestochecklist.domain.exception

open class DataSourceException(
    message: String,
	val code: String,
    cause: Throwable? = null
): Exception(message, cause)
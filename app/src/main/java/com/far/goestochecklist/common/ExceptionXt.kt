package com.far.goestochecklist.common

import com.far.goestochecklist.BuildConfig
import com.far.goestochecklist.domain.exception.DataSourceException

/*
 * Created by Filipi Andrade Rocha on 28/03/2023.
 */

fun Exception.printError() {
	if (BuildConfig.DEBUG) printStackTrace()
}

fun DataSourceException.formatErrorMessage() = when (code.isNotEmpty()) {
	true -> code.plus(": ${message.orEmpty().lowercase()}")
	false -> message.orEmpty().lowercase()
}
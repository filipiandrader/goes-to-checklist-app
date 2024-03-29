package com.far.goestochecklist.domain.core

import com.far.goestochecklist.common.printError
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

abstract class UseCase<T, in Params> {

	private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
	private val ioScope = Dispatchers.IO
	private val mainScope = Dispatchers.Main

	abstract fun run(params: Params? = null): Flow<T>

	operator fun invoke(
		params: Params? = null,
		onError: ((Throwable) -> Unit) = {},
		onSuccess: (T) -> Unit = {}
	) {
		applicationScope.launch(ioScope) {
			try {
				run(params).collect {
					withContext(mainScope) {
						onSuccess(it)
						return@withContext
					}
				}
			} catch (e: Exception) {
				withContext(mainScope) {
					e.printError()
					onError(e)
				}
			}
		}
	}
}
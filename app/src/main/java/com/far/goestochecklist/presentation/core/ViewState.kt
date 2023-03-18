package com.far.goestochecklist.presentation.core

import java.lang.RuntimeException

class ViewState<T>(
    val status: Status = Status.NEUTRAL,
    val data: T? = null,
    val error: Throwable? = null,
) {

    fun stateHandler(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit, loading: () -> Unit) {
        when (status) {
            Status.SUCCESS -> data?.let { onSuccess(it) } ?: throw RuntimeException()
            Status.ERROR -> error?.let { onError(it) } ?: throw RuntimeException()
            Status.LOADING -> loading()
            else -> Unit
        }
    }

    enum class Status {
        SUCCESS, ERROR, LOADING, NEUTRAL
    }
}

fun <T> ViewState<T>?.isLoading() = this?.status?.equals(ViewState.Status.LOADING) ?: false
fun <T> ViewState<T>?.isError() = this?.status?.equals(ViewState.Status.ERROR) ?: false
fun <T> ViewState<T>?.isNeutral() = this?.status?.equals(ViewState.Status.NEUTRAL) ?: false

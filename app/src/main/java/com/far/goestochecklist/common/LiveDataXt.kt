package com.far.goestochecklist.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.far.goestochecklist.presentation.core.ViewState
import com.far.goestochecklist.presentation.core.ViewState.Status.*

fun <T> MutableLiveData<ViewState<T>>.postNeutral() {
    value = ViewState(NEUTRAL, null, null)
}

fun <T> MutableLiveData<ViewState<T>>.postSuccess(data: T) {
    postValue(ViewState(SUCCESS, data, null))
}

fun <T> MutableLiveData<ViewState<T>>.postError(error: Throwable?) {
    postValue(ViewState(ERROR, null, error))
}

fun <T> MutableLiveData<ViewState<T>>.postLoading() {
    postValue(ViewState(LOADING, null, null))
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

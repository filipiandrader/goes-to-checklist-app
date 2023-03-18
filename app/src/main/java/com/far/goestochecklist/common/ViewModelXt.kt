package com.far.goestochecklist.common

import androidx.lifecycle.MutableLiveData
import com.far.goestochecklist.presentation.core.ViewState

fun <T> viewState() = lazy { MutableLiveData<ViewState<T>>() }

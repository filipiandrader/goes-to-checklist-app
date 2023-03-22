package com.far.goestochecklist.data.remote.utils

import com.far.goestochecklist.data.remote.dto.GenericResponse

interface RequestWrapper {

    suspend fun <T> wrapper(call: suspend () -> GenericResponse<T>): GenericResponse<T>
}
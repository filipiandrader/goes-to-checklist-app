package com.far.goestochecklist.data.remote.utils

interface RequestWrapper {

    suspend fun <T> wrapper(call: suspend () -> T): T
}
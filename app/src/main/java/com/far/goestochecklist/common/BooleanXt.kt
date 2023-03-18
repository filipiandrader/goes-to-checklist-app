package com.far.goestochecklist.common

fun Boolean?.orFalse() = this ?: false

fun Boolean?.orTrue() = this ?: true

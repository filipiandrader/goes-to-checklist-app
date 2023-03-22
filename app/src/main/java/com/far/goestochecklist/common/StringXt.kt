package com.far.goestochecklist.common

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

fun String?.isNotNullOrNotEmpty() = !isNullOrEmpty()
fun String.hasSpace() = this.contains(" ")
package com.far.goestochecklist.common

import java.text.SimpleDateFormat
import java.util.*

/*
 * Created by Filipi Andrade Rocha on 26/03/2023.
 */

fun String.toDate(format: String = "dd/MM/yyyy"): Date? {
	val sdf = SimpleDateFormat(format, Locale("pt", "BR"))
	return sdf.parse(this)
}

fun Date.format(format: String): String = SimpleDateFormat(
	format,
	Locale("pt", "BR")
).format(this)

fun Date.getYearNumber() = format("yyyy")
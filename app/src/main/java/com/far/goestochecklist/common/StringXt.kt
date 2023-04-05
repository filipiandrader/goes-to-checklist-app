package com.far.goestochecklist.common

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

fun String?.isNotNullOrNotEmpty() = !isNullOrEmpty()

fun String.hasSpace() = this.contains(" ")

fun String.getNameWhereToWatch() = when {
	this.lowercase().contains(Constants.PRIME_VIDEO) -> "Prime Video"
	this.lowercase().contains(Constants.APPLE_TV) -> "Apple TV"
	this.lowercase().contains(Constants.GOOGLE_PLAY) -> "Google Play"
	this.lowercase().contains(Constants.YOUTUBE) -> "Youtube"
	this.lowercase().contains(Constants.NOW) -> "NOW"
	this.lowercase().contains(Constants.CINEMA) -> "Cinema"
	this.lowercase().contains(Constants.NETFLIX) -> "Netflix"
	this.lowercase().contains(Constants.HBO_MAX) -> "HBO Max"
	this.lowercase().contains(Constants.DISNEY_PLUS) -> "Disney Plus"
	this.lowercase().contains(Constants.NEW_YORKER) -> "New Yorker"
	this.lowercase().contains(Constants.VIMEO) -> "Vimeo"
	else -> "Outro"
}

fun String.isStartWithHttp() = this.lowercase().startsWith("http")

fun String.getUserFirstName() = substringBefore(" ")

fun String?.isEqualTo(otherString: String?) = this.orEmpty().lowercase() == otherString.orEmpty().lowercase()

fun String?.isNotEqualTo(otherString: String?) = this.orEmpty().lowercase() == otherString.orEmpty().lowercase()
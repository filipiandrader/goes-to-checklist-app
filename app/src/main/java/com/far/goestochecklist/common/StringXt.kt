package com.far.goestochecklist.common

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

fun String?.isNotNullOrNotEmpty() = !isNullOrEmpty()

fun String.hasSpace() = this.contains(" ")

fun String.getNameWhereToWatch() = when {
	this.contains(Constants.PRIME_VIDEO) -> "Prime Video"
	this.contains(Constants.APPLE_TV) -> "Apple TV"
	this.contains(Constants.GOOGLE_PLAY) -> "Google Play"
	this.contains(Constants.YOUTUBE) -> "Youtube"
	this.contains(Constants.NOW) -> "NOW"
	this.contains(Constants.CINEMA) -> "Cinema"
	this.contains(Constants.NETFLIX) -> "Netflix"
	this.contains(Constants.HBO_MAX) -> "HBO Max"
	this.contains(Constants.DISNEY_PLUS) -> "Disney Plus"
	this.contains(Constants.NEW_YORKER) -> "New Yorker"
	this.contains(Constants.VIMEO) -> "Vimeo"
	else -> "Outro"
}
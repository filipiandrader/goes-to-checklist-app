package com.far.goestochecklist.domain.model

/*
 * Created by Filipi Andrade Rocha on 23/03/2023.
 */

data class Film(
	var year: String,
	var filmId: String,
	var name: String,
	var description: String,
	var releaseDate: String,
	var posterImage: String,
	var whereToWatch: String,
	var watched: Boolean = false
)

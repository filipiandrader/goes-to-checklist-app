package com.far.goestochecklist.data.remote.dto

import com.google.gson.annotations.SerializedName

/*
 * Created by Filipi Andrade Rocha on 23/03/2023.
 */

data class FilmResponse(
	var year: String? = null,
	@SerializedName("film_id") var filmId: String? = null,
	var name: String? = null,
	var description: String? = null,
	@SerializedName("release_date") var releaseDate: String? = null,
	@SerializedName("poster_image") var posterImage: String? = null,
	@SerializedName("where_to_watch") var whereToWatch: List<String>? = null,
	var duration: String? = null,
	var category: List<String>? = null,
	var watched: Boolean = false
)

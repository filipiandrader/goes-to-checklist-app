package com.far.goestochecklist.data.remote.dto

import com.google.gson.annotations.SerializedName

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

data class MarkWatchRequest(
	@SerializedName("film_id") var filmId: String,
	@SerializedName("user_id") var userId: String
)

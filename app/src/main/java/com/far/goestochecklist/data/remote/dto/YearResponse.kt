package com.far.goestochecklist.data.remote.dto

import com.google.gson.annotations.SerializedName

/*
 * Created by Filipi Andrade Rocha on 23/03/2023.
 */

data class YearResponse(
	@SerializedName("year_id") var yearId: String? = null,
	var year: String? = null
)

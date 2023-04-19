package com.far.goestochecklist.data.remote.dto

/*
 * Created by Filipi Andrade Rocha on 18/04/2023.
 */

data class FilterResponse(
	var year: List<YearResponse>? = null,
	var category: List<CategoryResponse>? = null
)

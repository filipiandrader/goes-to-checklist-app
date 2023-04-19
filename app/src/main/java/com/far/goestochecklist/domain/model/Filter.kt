package com.far.goestochecklist.domain.model

/*
 * Created by Filipi Andrade Rocha on 18/04/2023.
 */

data class Filter(
	var year: List<Year>,
	var category: List<Category>
)

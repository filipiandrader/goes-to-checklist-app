package com.far.goestochecklist.data.remote.dto

import com.google.gson.annotations.SerializedName

/*
 * Created by Filipi Andrade Rocha on 18/04/2023.
 */

data class CategoryResponse(
	@SerializedName("category_id") var categoryId: String? = null,
	var name: String? = null
)

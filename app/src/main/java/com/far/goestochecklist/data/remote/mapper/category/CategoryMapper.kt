package com.far.goestochecklist.data.remote.mapper.category

import com.far.goestochecklist.data.remote.dto.CategoryResponse
import com.far.goestochecklist.data.remote.mapper.core.DataRemoteMapper
import com.far.goestochecklist.domain.model.Category

/*
 * Created by Filipi Andrade Rocha on 18/04/2023.
 */

object CategoryMapper : DataRemoteMapper<CategoryResponse, Category> {

	override fun toDomain(data: CategoryResponse) = Category(
		categoryId = data.categoryId.orEmpty(),
		name = data.name.orEmpty()
	)
}
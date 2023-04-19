package com.far.goestochecklist.data.remote.mapper.filter

import com.far.goestochecklist.data.remote.dto.FilterResponse
import com.far.goestochecklist.data.remote.mapper.category.CategoryMapper
import com.far.goestochecklist.data.remote.mapper.core.DataRemoteMapper
import com.far.goestochecklist.data.remote.mapper.year.YearMapper
import com.far.goestochecklist.domain.model.Filter

/*
 * Created by Filipi Andrade Rocha on 18/04/2023.
 */

object FilterMapper : DataRemoteMapper<FilterResponse, Filter> {

	override fun toDomain(data: FilterResponse) = Filter(
		year = YearMapper.toDomain(data.year.orEmpty()),
		category = CategoryMapper.toDomain(data.category.orEmpty())
	)
}
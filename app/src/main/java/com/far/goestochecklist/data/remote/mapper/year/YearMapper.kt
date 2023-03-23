package com.far.goestochecklist.data.remote.mapper.year

import com.far.goestochecklist.data.remote.dto.YearResponse
import com.far.goestochecklist.data.remote.mapper.core.DataRemoteMapper
import com.far.goestochecklist.domain.model.Year

/*
 * Created by Filipi Andrade Rocha on 23/03/2023.
 */

object YearMapper : DataRemoteMapper<YearResponse, Year> {

	override fun toDomain(data: YearResponse) = Year(
		yearId = data.yearId.orEmpty(),
		year = data.year.orEmpty()
	)
}
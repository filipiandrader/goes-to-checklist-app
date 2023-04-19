package com.far.goestochecklist.domain.usecase.filter

import com.far.goestochecklist.domain.core.UseCase
import com.far.goestochecklist.domain.model.Filter
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 18/04/2023.
 */

class GetFiltersUseCase @Inject constructor(private val repository: GoesToChecklistRepository) :
	UseCase<Filter, Unit>() {

	override fun run(params: Unit?) = repository.getFilters()
}
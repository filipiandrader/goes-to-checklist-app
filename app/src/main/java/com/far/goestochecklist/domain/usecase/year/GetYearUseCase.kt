package com.far.goestochecklist.domain.usecase.year

import com.far.goestochecklist.domain.core.UseCase
import com.far.goestochecklist.domain.model.Year
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 23/03/2023.
 */

class GetYearUseCase @Inject constructor(private val repository: GoesToChecklistRepository) :
	UseCase<List<Year>, Unit>() {

	override fun run(params: Unit?) = repository.getYear()
}
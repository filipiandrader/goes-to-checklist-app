package com.far.goestochecklist.domain.usecase.film

import com.far.goestochecklist.domain.core.UseCase
import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 18/04/2023.
 */

class GetFilmByFiltersUseCase @Inject constructor(private val repository: GoesToChecklistRepository) :
	UseCase<List<Film>, GetFilmByFiltersUseCase.Params>() {

	override fun run(params: Params?) = repository.getFilmByFilters(
		params?.categoryName.orEmpty(),
		params?.year.orEmpty(),
		params?.filmName.orEmpty()
	)

	data class Params(
		var categoryName: String = "",
		var year: String = "",
		var filmName: String = ""
	)
}
package com.far.goestochecklist.data.remote.mapper.film

import com.far.goestochecklist.data.remote.dto.FilmResponse
import com.far.goestochecklist.data.remote.mapper.core.DataRemoteMapper
import com.far.goestochecklist.domain.model.Film

/*
 * Created by Filipi Andrade Rocha on 23/03/2023.
 */

object FilmMapper : DataRemoteMapper<FilmResponse, Film> {

	override fun toDomain(data: FilmResponse) = Film(
		id = data.id ?: -1,
		year = data.year.orEmpty(),
		filmId = data.filmId.orEmpty(),
		name = data.name.orEmpty(),
		description = data.description.orEmpty(),
		releaseDate = data.releaseDate.orEmpty(),
		posterImage = data.posterImage.orEmpty(),
		whereToWatch = data.whereToWatch.orEmpty(),
		duration = data.duration.orEmpty(),
		category = data.category.orEmpty(),
		watched = data.watched
	)
}
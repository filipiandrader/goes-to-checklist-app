package com.far.goestochecklist.data.mapper

/*
 * Created by Filipi Andrade Rocha on 31/01/2023.
 */

interface DataRemoteMapper<R, D> {

	fun toDomain(data: R): D
	fun toDomain(data: List<R>) = data.map { toDomain(it) }
}
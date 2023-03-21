package com.far.goestochecklist.data.local.mapper

/*
 * Created by Filipi Andrade Rocha on 20/03/2023.
 */

interface DataLocalMapper<L, D> {

	fun toLocal(domain: D): L
	fun fromLocal(local: L): D

	fun toLocal(domain: List<D>) = domain.map { toLocal(it) }

	fun fromLocal(local: List<L>) = local.map { fromLocal(it) }
}
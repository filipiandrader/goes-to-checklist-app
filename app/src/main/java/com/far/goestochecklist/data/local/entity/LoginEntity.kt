package com.far.goestochecklist.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * Created by Filipi Andrade Rocha on 20/03/2023.
 */

@Entity
data class LoginEntity(
	@PrimaryKey var id: Int? = null,
	val userId: String,
	val name: String,
	val username: String
)

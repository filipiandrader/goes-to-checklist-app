package com.far.goestochecklist.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.far.goestochecklist.data.local.dao.UserDao
import com.far.goestochecklist.data.local.entity.LoginEntity

/*
 * Created by Filipi Andrade Rocha on 20/03/2023.
 */

@Database(
	entities = [LoginEntity::class],
	version = 1,
	exportSchema = false
)
abstract class GoesToChecklistDatabase : RoomDatabase() {

	abstract val userDao: UserDao
}
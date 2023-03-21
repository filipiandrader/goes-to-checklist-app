package com.far.goestochecklist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.far.goestochecklist.data.local.entity.LoginEntity

/*
 * Created by Filipi Andrade Rocha on 20/03/2023.
 */

@Dao
interface UserDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertUser(login: LoginEntity)

	@Query("DELETE FROM loginentity")
	suspend fun deleteUser()

	@Query("SELECT * FROM loginentity")
	suspend fun getUser(): LoginEntity?
}
package com.far.goestochecklist.data.local.dao

import androidx.room.*
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

	@Query("UPDATE loginentity SET name = :name, username = :username WHERE userId = :userId")
	suspend fun updateUserInfo(userId: String, name: String, username: String)
}
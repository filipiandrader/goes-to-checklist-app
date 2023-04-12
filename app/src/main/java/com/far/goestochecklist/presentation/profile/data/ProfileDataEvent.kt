package com.far.goestochecklist.presentation.profile.data

import com.far.goestochecklist.domain.model.Login

/*
 * Created by Filipi Andrade Rocha on 04/04/2023.
 */

sealed class ProfileDataEvent {
	object GetUserSubmit : ProfileDataEvent()
	data class GetUserSuccess(val user: Login) : ProfileDataEvent()
	data class GetUserError(val throwable: Throwable) : ProfileDataEvent()
}

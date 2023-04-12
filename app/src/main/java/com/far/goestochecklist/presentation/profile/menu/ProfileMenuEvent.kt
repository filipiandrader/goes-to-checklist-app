package com.far.goestochecklist.presentation.profile.menu

import com.far.goestochecklist.domain.model.Login

/*
 * Created by Filipi Andrade Rocha on 04/04/2023.
 */

sealed class ProfileMenuEvent {
	object LogoutSubmit : ProfileMenuEvent()
	object LogoutSuccess : ProfileMenuEvent()
	data class LogoutError(val throwable: Throwable) : ProfileMenuEvent()
}

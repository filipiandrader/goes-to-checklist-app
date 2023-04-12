package com.far.goestochecklist.ui.screens.profile.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.far.goestochecklist.BuildConfig
import com.far.goestochecklist.R
import com.far.goestochecklist.common.formatErrorMessage
import com.far.goestochecklist.domain.exception.DataSourceException
import com.far.goestochecklist.presentation.profile.menu.ProfileMenuEvent.*
import com.far.goestochecklist.presentation.profile.menu.ProfileMenuViewModel
import com.far.goestochecklist.ui.components.dialog.GoesToChecklistDialog
import com.far.goestochecklist.ui.components.menu.GoesToChecklistMenuItem
import com.far.goestochecklist.ui.navigation.Routes
import com.far.goestochecklist.ui.navigation.doNavigation
import com.far.goestochecklist.ui.theme.Gray300
import com.far.goestochecklist.ui.theme.Gray800
import com.far.goestochecklist.ui.theme.Gray900
import com.far.goestochecklist.ui.theme.Yellow

/*
 * Created by Filipi Andrade Rocha on 11/04/2023.
 */

@Composable
fun ProfileMenuScreen(
	navController: NavController,
	bottomNavController: NavController,
	viewModel: ProfileMenuViewModel = hiltViewModel()
) {
	var showLogoutDialog by remember { mutableStateOf(false) }
	var showLogoutErrorDialog by remember { mutableStateOf(false) }
	var errorMessage by remember { mutableStateOf("") }

	LaunchedEffect(key1 = true) {
		viewModel.profileMenuEventChannel.collect { event ->
			when (event) {
				is LogoutSuccess -> doNavigation(Routes.Logout, navController)
				is LogoutError -> {
					showLogoutErrorDialog = true
					errorMessage = if (event.throwable is DataSourceException) {
						event.throwable.formatErrorMessage()
					} else {
						event.throwable.message.orEmpty()
					}
				}
				else -> Unit
			}
		}
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(Gray900)
	) {
		Column(modifier = Modifier.fillMaxSize()) {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(205.dp)
			) {
				TopAppBar(
					modifier = Modifier
						.fillMaxWidth()
						.height(130.dp),
					backgroundColor = Yellow,
				) {
					Box(
						modifier = Modifier
							.padding(start = 8.dp)
							.clickable { bottomNavController.popBackStack() }
					) {
						Image(
							modifier = Modifier.size(32.dp),
							painter = painterResource(id = R.drawable.ic_back_arrow_gray),
							contentDescription = stringResource(id = R.string.content_description_back_button)
						)
					}
				}
				Image(
					modifier = Modifier
						.size(150.dp)
						.background(color = Gray800, shape = CircleShape)
						.align(Alignment.BottomCenter),
					painter = painterResource(id = R.drawable.ic_profile),
					contentDescription = stringResource(id = R.string.content_description_profile_picture)
				)
			}
			Spacer(modifier = Modifier.size(8.dp))
			GoesToChecklistMenuItem(
				modifier = Modifier
					.height(48.dp)
					.fillMaxWidth()
					.clickable { bottomNavController.popBackStack() },
				icon = R.drawable.ic_home_outline,
				title = stringResource(id = R.string.profile_menu_home_title)
			)
			Divider(
				modifier = Modifier
					.fillMaxWidth()
					.height(0.5.dp),
				color = Gray300
			)
			GoesToChecklistMenuItem(
				modifier = Modifier
					.height(48.dp)
					.fillMaxWidth()
					.clickable { doNavigation(Routes.ProfileData, bottomNavController) },
				icon = R.drawable.ic_profile_outline,
				title = stringResource(id = R.string.profile_menu_data_title)
			)
			Divider(
				modifier = Modifier
					.fillMaxWidth()
					.height(0.5.dp),
				color = Gray300
			)
			GoesToChecklistMenuItem(
				modifier = Modifier
					.height(48.dp)
					.fillMaxWidth()
					.clickable { doNavigation(Routes.ChangePassword, bottomNavController) },
				icon = R.drawable.ic_password,
				title = stringResource(id = R.string.profile_menu_password_title)
			)
			Divider(
				modifier = Modifier
					.fillMaxWidth()
					.height(0.5.dp),
				color = Gray300
			)
			GoesToChecklistMenuItem(
				modifier = Modifier
					.height(48.dp)
					.fillMaxWidth()
					.clickable { showLogoutDialog = true },
				icon = R.drawable.ic_logout,
				title = stringResource(id = R.string.profile_menu_logout_title)
			)
		}

		if (showLogoutDialog) {
			GoesToChecklistDialog(
				modifier = Modifier
					.width(450.dp)
					.wrapContentHeight()
					.padding(horizontal = 16.dp, vertical = 24.dp),
				title = stringResource(id = R.string.logout_dialog_title),
				textContent = stringResource(id = R.string.logout_dialog_content),
				positiveText = stringResource(id = R.string.yes),
				onPositiveClick = {
					showLogoutDialog = false
					viewModel.onEvent(LogoutSubmit)
				},
				negativeText = stringResource(id = R.string.no),
				onNegativeClick = { showLogoutDialog = false }
			)
		}

		if (showLogoutErrorDialog) {
			GoesToChecklistDialog(
				modifier = Modifier
					.width(450.dp)
					.wrapContentHeight()
					.padding(horizontal = 16.dp, vertical = 24.dp),
				textContent = errorMessage,
				positiveText = stringResource(id = R.string.try_again),
				onPositiveClick = {
					showLogoutErrorDialog = false
					viewModel.onEvent(LogoutSubmit)
				},
				negativeText = stringResource(id = R.string.no),
				onNegativeClick = { showLogoutErrorDialog = false }
			)
		}

		Text(
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.padding(bottom = 4.dp),
			text = stringResource(id = R.string.version_info, BuildConfig.VERSION_NAME),
			style = MaterialTheme.typography.caption
		)
	}
}
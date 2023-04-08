package com.far.goestochecklist.ui.screens.profile.data

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.navigation.NavController
import com.far.goestochecklist.R
import com.far.goestochecklist.common.Constants.USER_QUERY_NAME
import com.far.goestochecklist.common.OnLifecycleEvent
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.presentation.profile.data.ProfileEvent.*
import com.far.goestochecklist.presentation.profile.data.ProfileViewModel
import com.far.goestochecklist.ui.components.button.GoesToChecklistButton
import com.far.goestochecklist.ui.components.dialog.GoesToChecklistDialog
import com.far.goestochecklist.ui.components.textfield.GoesToChecklistTextField
import com.far.goestochecklist.ui.navigation.Routes
import com.far.goestochecklist.ui.navigation.doNavigation
import com.far.goestochecklist.ui.theme.Gray800
import com.far.goestochecklist.ui.theme.Gray900
import com.far.goestochecklist.ui.theme.Yellow
import com.google.gson.Gson

/*
 * Created by Filipi Andrade Rocha on 02/04/2023.
 */

@Composable
fun ProfileScreen(
	navController: NavController,
	viewModel: ProfileViewModel = hiltViewModel()
) {

	var userInfo by remember { mutableStateOf<Login?>(null) }
	var isLoading by remember { mutableStateOf(true) }
	var isLogoutLoading by remember { mutableStateOf(false) }
	var showLogoutDialog by remember { mutableStateOf(false) }

	OnLifecycleEvent { _, event ->
		when (event) {
			ON_RESUME -> viewModel.onEvent(GetUserSubmit)
			else -> Unit
		}
	}

	LaunchedEffect(key1 = true) {
		viewModel.profileEventChannel.collect { event ->
			when (event) {
				is GetUserSuccess -> {
					userInfo = event.user
					isLoading = false
				}
				is LogoutSuccess -> doNavigation(Routes.Logout, navController)
				is LogoutError -> {
					// TODO MOSTRAR ERRO
				}
				else -> Unit
			}
		}
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(color = Gray900)
	) {
		Column(modifier = Modifier.fillMaxSize()) {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(235.dp)
			) {
				TopAppBar(
					modifier = Modifier
						.fillMaxWidth()
						.height(150.dp),
					backgroundColor = Yellow,
				) {
					Box(
						modifier = Modifier
							.padding(start = 8.dp)
							.clickable { if (!isLoading) navController.popBackStack() }
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
						.size(180.dp)
						.background(color = Gray800, shape = CircleShape)
						.align(Alignment.BottomCenter),
					painter = painterResource(id = R.drawable.ic_profile),
					contentDescription = stringResource(id = R.string.content_description_profile_picture)
				)
			}

			ShimmerProfileItem(
				modifier = Modifier
					.fillMaxSize()
					.padding(16.dp),
				isLoading = isLoading
			) {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(16.dp)
				) {
					GoesToChecklistTextField(
						modifier = Modifier
							.fillMaxWidth()
							.height(48.dp),
						textFieldValue = userInfo?.name.orEmpty(),
						leadingIcon = R.drawable.ic_alphabetical,
						labelText = stringResource(id = R.string.name_placeholder),
						labelTextStyle = MaterialTheme.typography.body2,
						labelTextColor = Color.White,
						isEnabled = false
					)
					Spacer(modifier = Modifier.size(16.dp))
					GoesToChecklistTextField(
						modifier = Modifier
							.fillMaxWidth()
							.height(48.dp),
						textFieldValue = userInfo?.username.orEmpty(),
						leadingIcon = R.drawable.ic_user,
						labelText = stringResource(id = R.string.username_placeholder),
						labelTextStyle = MaterialTheme.typography.body2,
						labelTextColor = Color.White,
						isEnabled = false
					)
					Spacer(modifier = Modifier.size(16.dp))
					GoesToChecklistButton(
						modifier = Modifier
							.fillMaxWidth()
							.height(48.dp),
						buttonText = stringResource(id = R.string.edit_profile_button_text),
						isEnable = !isLoading,
						onClick = {
							val bundle = Bundle()
							bundle.putString(USER_QUERY_NAME, Gson().toJson(userInfo))
							doNavigation(Routes.EditProfileData, navController, bundle)
						}
					)
					Spacer(modifier = Modifier.size(8.dp))
					GoesToChecklistButton(
						modifier = Modifier
							.fillMaxWidth()
							.height(48.dp),
						buttonText = stringResource(id = R.string.logout_button_text),
						isEnable = !isLogoutLoading,
						isLoading = isLogoutLoading,
						onClick = { showLogoutDialog = true }
					)
				}
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
						isLogoutLoading = true
						viewModel.onEvent(LogoutSubmit)
					},
					negativeText = stringResource(id = R.string.no),
					onNegativeClick = { showLogoutDialog = false }
				)
			}
		}
	}
}
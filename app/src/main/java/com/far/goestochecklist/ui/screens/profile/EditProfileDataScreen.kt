package com.far.goestochecklist.ui.screens.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.far.goestochecklist.R
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.ui.components.button.GoesToChecklistButton
import com.far.goestochecklist.ui.components.textfield.GoesToChecklistTextField
import com.far.goestochecklist.ui.theme.Gray800
import com.far.goestochecklist.ui.theme.Gray900
import com.far.goestochecklist.ui.theme.Yellow

/*
 * Created by Filipi Andrade Rocha on 03/04/2023.
 */

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun EditProfileDataScreen(
	navController: NavController,
	userInfo: Login
) {

	var isNameError by remember { mutableStateOf(false) }
	var nameErrorMessage by remember { mutableStateOf("") }
	var isUsernameError by remember { mutableStateOf(false) }
	var usernameErrorMessage by remember { mutableStateOf("") }
	var showButtonLoading by remember { mutableStateOf(false) }
	var isButtonEnabled by remember { mutableStateOf(false) }
	var errorMessage by remember { mutableStateOf("") }
	var showErrorDialog by remember { mutableStateOf(false) }

	val (nameTextField, usernameTextField) = remember { FocusRequester.createRefs() }
	val keyboardController = LocalSoftwareKeyboardController.current
	val bringIntoViewRequester = BringIntoViewRequester()
	val coroutineScope = rememberCoroutineScope()

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(color = Gray900)
	) {
		Column(modifier = Modifier.fillMaxSize()) {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(185.dp)
			) {
				TopAppBar(
					modifier = Modifier
						.fillMaxWidth()
						.height(100.dp),
					backgroundColor = Yellow,
				) {
					Box(
						modifier = Modifier
							.padding(8.dp)
							.clickable { navController.popBackStack() }
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
			Column(
				modifier = Modifier
					.fillMaxSize()
					.padding(16.dp)
			) {
				var name by remember { mutableStateOf(userInfo.name) }
				GoesToChecklistTextField(
					modifier = Modifier
						.fillMaxWidth()
						.height(48.dp)
						.focusRequester(nameTextField),
					textFieldValue = name,
					onValueChange = {
						isNameError = false
						nameErrorMessage = ""
						name = it
//						viewModel.onEvent(ValidateNameSubmit(name))
					},
					keyboardOptions = KeyboardOptions(
						keyboardType = KeyboardType.Text,
						imeAction = ImeAction.Next
					),
					keyboardActions = KeyboardActions(
						onNext = { usernameTextField.requestFocus() }
					),
					leadingIcon = R.drawable.ic_alphabetical,
					labelText = stringResource(id = R.string.name_placeholder),
					labelTextStyle = MaterialTheme.typography.body2,
					labelTextColor = Color.White,
					isError = isNameError,
					errorMessage = nameErrorMessage
				)
				Spacer(modifier = Modifier.size(16.dp))
				var username by remember { mutableStateOf(userInfo.username) }
				GoesToChecklistTextField(
					modifier = Modifier
						.fillMaxWidth()
						.height(48.dp)
						.focusRequester(usernameTextField),
					textFieldValue = username,
					onValueChange = {
						isUsernameError = false
						usernameErrorMessage = ""
						username = it
//						viewModel.onEvent(ValidateUsernameSubmit(username))
					},
					keyboardOptions = KeyboardOptions(
						keyboardType = KeyboardType.Text,
						imeAction = ImeAction.Go
					),
					keyboardActions = KeyboardActions(
						onGo = {
							isButtonEnabled = false
							showButtonLoading = true
							keyboardController?.hide()
//							viewModel.onEvent(SignUpSubmit)
						}
					),
					leadingIcon = R.drawable.ic_user,
					labelText = stringResource(id = R.string.username_placeholder),
					labelTextStyle = MaterialTheme.typography.body2,
					labelTextColor = Color.White,
					isError = isUsernameError,
					errorMessage = usernameErrorMessage
				)
				Spacer(modifier = Modifier.size(16.dp))
				GoesToChecklistButton(
					modifier = Modifier
						.fillMaxWidth()
						.height(48.dp),
					buttonText = stringResource(id = R.string.edit_profile_data_button_text),
					isEnable = isButtonEnabled,
					isLoading = showButtonLoading,
					onClick = {
						// TODO FAZER INTEGRAÇÃO PARA ATUALIZAR DADOS
						isButtonEnabled = false
						showButtonLoading = true
						keyboardController?.hide()
					}
				)
			}
		}
	}
}
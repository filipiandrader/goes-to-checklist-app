package com.far.goestochecklist.ui.screens.signup

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.far.goestochecklist.R
import com.far.goestochecklist.common.formatErrorMessage
import com.far.goestochecklist.common.isNotNullOrNotEmpty
import com.far.goestochecklist.domain.exception.DataSourceException
import com.far.goestochecklist.presentation.signup.SignUpEvent.*
import com.far.goestochecklist.presentation.signup.SignUpViewModel
import com.far.goestochecklist.ui.components.button.GoesToChecklistButton
import com.far.goestochecklist.ui.components.dialog.GoesToChecklistDialog
import com.far.goestochecklist.ui.components.textfield.GoesToChecklistTextField
import com.far.goestochecklist.ui.navigation.Routes
import com.far.goestochecklist.ui.navigation.doNavigation
import com.far.goestochecklist.ui.theme.Gray500
import com.far.goestochecklist.ui.theme.Gray900
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
 * Created by Filipi Andrade Rocha on 28/02/2023.
 */

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun SignUpScreen(
	navController: NavController,
	viewModel: SignUpViewModel = hiltViewModel()
) {

	var isNameError by remember { mutableStateOf(false) }
	var nameErrorMessage by remember { mutableStateOf("") }
	var isUsernameError by remember { mutableStateOf(false) }
	var usernameErrorMessage by remember { mutableStateOf("") }
	var isPasswordError by remember { mutableStateOf(false) }
	var passwordErrorMessage by remember { mutableStateOf("") }
	var showButtonLoading by remember { mutableStateOf(false) }
	var isButtonEnabled by remember { mutableStateOf(false) }
	var errorMessage by remember { mutableStateOf("") }
	var showErrorDialog by remember { mutableStateOf(false) }

	val (nameTextField, usernameTextField, passwordTextField) = remember { FocusRequester.createRefs() }
	val keyboardController = LocalSoftwareKeyboardController.current
	val bringIntoViewRequester = BringIntoViewRequester()
	val coroutineScope = rememberCoroutineScope()

	LaunchedEffect(key1 = true) {
		viewModel.validationEventChannel.collect { event ->
			when (event) {
				is ValidateNameError -> {
					isNameError = true
					nameErrorMessage = event.throwable.message.orEmpty()
					isButtonEnabled = false
					showButtonLoading = false
				}
				is ValidateUsernameError -> {
					isUsernameError = true
					usernameErrorMessage = event.throwable.message.orEmpty()
					isButtonEnabled = false
					showButtonLoading = false
				}
				is ValidatePasswordError -> {
					isPasswordError = true
					passwordErrorMessage = event.throwable.message.orEmpty()
					isButtonEnabled = false
					showButtonLoading = false
				}
				is ValidToLogin -> isButtonEnabled = event.valid
				is SignUpError -> {
					showButtonLoading = false
					showErrorDialog = true
					errorMessage = if (event.throwable is DataSourceException) {
						event.throwable.formatErrorMessage()
					} else {
						event.throwable.message.orEmpty()
					}
				}
				is LoginSuccess -> doNavigation(Routes.Home, navController)
				else -> Unit
			}
		}
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(color = Gray900)
	) {
		Column {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.weight(0.7f)
					.paint(
						painterResource(id = R.drawable.ic_yellow_background),
						contentScale = ContentScale.FillBounds
					)
			) {
				Box(
					modifier = Modifier
						.padding(8.dp)
						.clickable { navController.popBackStack() }
				) {
					Image(
						modifier = Modifier
							.size(32.dp)
							.align(Alignment.TopStart),
						painter = painterResource(id = R.drawable.ic_back_arrow_gray),
						contentDescription = stringResource(id = R.string.content_description_back_button)
					)
				}
				Image(
					modifier = Modifier.align(Alignment.Center),
					painter = painterResource(id = R.drawable.ic_logo),
					contentDescription = stringResource(id = R.string.content_description_logo)
				)
			}

			Column(
				modifier = Modifier
					.fillMaxSize()
					.weight(1.0f)
					.padding(start = 16.dp, end = 16.dp, top = 24.dp)
			) {
				var name by remember { mutableStateOf("") }
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
						viewModel.onEvent(ValidateNameSubmit(name))
					},
					keyboardOptions = KeyboardOptions(
						keyboardType = KeyboardType.Text,
						imeAction = ImeAction.Next
					),
					keyboardActions = KeyboardActions(
						onNext = { usernameTextField.requestFocus() }
					),
					leadingIcon = R.drawable.ic_alphabetical,
					placeholder = {
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.CenterStart
						) {
							Text(
								text = stringResource(id = R.string.name_placeholder),
								style = MaterialTheme.typography.body2,
								fontWeight = FontWeight.Normal,
								color = Gray500
							)
						}
					},
					isError = isNameError,
					errorMessage = nameErrorMessage
				)
				Spacer(modifier = Modifier.size(16.dp))
				var username by remember { mutableStateOf("") }
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
						viewModel.onEvent(ValidateUsernameSubmit(username))
					},
					keyboardOptions = KeyboardOptions(
						keyboardType = KeyboardType.Email,
						imeAction = ImeAction.Next
					),
					keyboardActions = KeyboardActions(
						onNext = { passwordTextField.requestFocus() }
					),
					leadingIcon = R.drawable.ic_user,
					placeholder = {
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.CenterStart
						) {
							Text(
								text = stringResource(id = R.string.username_placeholder),
								style = MaterialTheme.typography.body2,
								fontWeight = FontWeight.Normal,
								color = Gray500
							)
						}
					},
					isError = isUsernameError,
					errorMessage = usernameErrorMessage
				)
				Spacer(modifier = Modifier.size(16.dp))
				var password by remember { mutableStateOf("") }
				GoesToChecklistTextField(
					modifier = Modifier
						.fillMaxWidth()
						.height(48.dp)
						.focusRequester(passwordTextField)
						.bringIntoViewRequester(bringIntoViewRequester)
						.onFocusChanged { focusState ->
							if (focusState.isFocused) {
								coroutineScope.launch {
									delay(250)
									bringIntoViewRequester.bringIntoView()
								}
							}
						},
					textFieldValue = password,
					onValueChange = {
						isPasswordError = false
						passwordErrorMessage = ""
						password = it
						viewModel.onEvent(ValidatePasswordSubmit(password))
					},
					keyboardOptions = KeyboardOptions(
						keyboardType = KeyboardType.Password,
						imeAction = ImeAction.Go
					),
					keyboardActions = KeyboardActions(
						onGo = {
							isButtonEnabled = false
							showButtonLoading = true
							keyboardController?.hide()
							viewModel.onEvent(SignUpSubmit)
						}
					),
					leadingIcon = R.drawable.ic_lock,
					placeholder = {
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.CenterStart
						) {
							Text(
								text = stringResource(id = R.string.password_placeholder),
								style = MaterialTheme.typography.body2,
								fontWeight = FontWeight.Normal,
								color = Gray500
							)
						}
					},
					passwordToggleOption = true,
					isError = isPasswordError,
					errorMessage = passwordErrorMessage
				)
				Spacer(modifier = Modifier.size(16.dp))
				GoesToChecklistButton(
					modifier = Modifier
						.fillMaxWidth()
						.height(48.dp),
					buttonText = stringResource(id = R.string.create_account_label),
					isEnable = isButtonEnabled,
					isLoading = showButtonLoading,
					onClick = {
						isButtonEnabled = false
						showButtonLoading = true
						keyboardController?.hide()
						viewModel.onEvent(SignUpSubmit)
					}
				)
			}
		}

		if (errorMessage.isNotNullOrNotEmpty() && showErrorDialog) {
			GoesToChecklistDialog(
				modifier = Modifier
					.width(400.dp)
					.wrapContentHeight()
					.padding(horizontal = 16.dp, vertical = 24.dp),
				textContent = errorMessage,
				positiveText = stringResource(id = R.string.ok),
				onPositiveClick = { showErrorDialog = false }
			)
		}
	}
}

@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)

package com.far.goestochecklist.ui.screens.password

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.far.goestochecklist.R
import com.far.goestochecklist.common.formatErrorMessage
import com.far.goestochecklist.common.isNotNullOrNotEmpty
import com.far.goestochecklist.domain.exception.DataSourceException
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ChangePasswordError
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ChangePasswordSubmit
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ChangePasswordSuccess
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidToChangePassword
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidToChangePasswordError
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidateConfirmNewPasswordError
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidateConfirmNewPasswordSubmit
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidateNewPasswordError
import com.far.goestochecklist.presentation.password.ChangePasswordEvent.ValidateNewPasswordSubmit
import com.far.goestochecklist.presentation.password.ChangePasswordViewModel
import com.far.goestochecklist.ui.components.button.GoesToChecklistButton
import com.far.goestochecklist.ui.components.dialog.GoesToChecklistDialog
import com.far.goestochecklist.ui.components.textfield.GoesToChecklistTextField
import com.far.goestochecklist.ui.theme.Gray500
import com.far.goestochecklist.ui.theme.Gray900
import com.far.goestochecklist.ui.theme.Yellow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
 * Created by Filipi Andrade Rocha on 11/04/2023.
 */

@Composable
fun ChangePasswordScreen(
	bottomNavController: NavController,
	viewModel: ChangePasswordViewModel = hiltViewModel()
) {

	var isNewPasswordError by remember { mutableStateOf(false) }
	var newPasswordErrorMessage by remember { mutableStateOf("") }
	var isConfirmNewPasswordError by remember { mutableStateOf(false) }
	var confirmNewPasswordErrorMessage by remember { mutableStateOf("") }
	var showButtonLoading by remember { mutableStateOf(false) }
	var isButtonEnabled by remember { mutableStateOf(false) }
	var errorMessage by remember { mutableStateOf("") }
	var showErrorDialog by remember { mutableStateOf(false) }
	var showSuccessDialog by remember { mutableStateOf(false) }

	val (newPasswordTextField, confirmNewPasswordTextField) = remember { FocusRequester.createRefs() }
	val keyboardController = LocalSoftwareKeyboardController.current
	val bringIntoViewRequester = BringIntoViewRequester()
	val coroutineScope = rememberCoroutineScope()

	LaunchedEffect(key1 = true) {
		viewModel.changePasswordEventChannel.collect { event ->
			when (event) {
				is ValidateNewPasswordError -> {
					isNewPasswordError = true
					newPasswordErrorMessage = event.throwable.message.orEmpty()
					isButtonEnabled = false
					showButtonLoading = false
				}

				is ValidateConfirmNewPasswordError -> {
					isConfirmNewPasswordError = true
					confirmNewPasswordErrorMessage = event.throwable.message.orEmpty()
					isButtonEnabled = false
					showButtonLoading = false
				}

				is ValidToChangePassword -> {
					isNewPasswordError = false
					newPasswordErrorMessage = ""
					isConfirmNewPasswordError = false
					confirmNewPasswordErrorMessage = ""
					isButtonEnabled = event.valid
				}

				is ValidToChangePasswordError -> {
					isNewPasswordError = true
					newPasswordErrorMessage = event.throwable.message.orEmpty()
					isConfirmNewPasswordError = true
					confirmNewPasswordErrorMessage = event.throwable.message.orEmpty()
					isButtonEnabled = false
					showButtonLoading = false
				}

				is ChangePasswordSuccess -> {
					showButtonLoading = false
					showSuccessDialog = true
				}

				is ChangePasswordError -> {
					showButtonLoading = false
					showErrorDialog = true
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
			.background(color = Gray900)
	) {
		Column(modifier = Modifier.fillMaxSize()) {
			Box(
				modifier = Modifier.wrapContentSize()
			) {
				TopAppBar(
					modifier = Modifier
						.fillMaxWidth()
						.height(95.dp),
					backgroundColor = Yellow,
					title = {
						Box(
							modifier = Modifier.padding(top = 30.dp)
						) {
							Text(
								text = stringResource(id = R.string.change_password_title),
								style = MaterialTheme.typography.h4,
								color = Gray900
							)
						}
					},
					navigationIcon = {
						Box(
							modifier = Modifier
								.padding(start = 8.dp, top = 30.dp)
								.clickable { bottomNavController.popBackStack() }
						) {
							Image(
								modifier = Modifier.size(32.dp),
								painter = painterResource(id = R.drawable.ic_back_arrow_gray),
								contentDescription = stringResource(id = R.string.content_description_back_button)
							)
						}
					}
				)
			}
			Column(
				modifier = Modifier
					.fillMaxSize()
					.padding(horizontal = 16.dp)
			) {
				Spacer(modifier = Modifier.size(16.dp))
				var newPassword by remember { mutableStateOf("") }
				GoesToChecklistTextField(
					modifier = Modifier
						.fillMaxWidth()
						.height(48.dp)
						.focusRequester(newPasswordTextField),
					textFieldValue = newPassword,
					onValueChange = {
						isNewPasswordError = false
						newPasswordErrorMessage = ""
						newPassword = it
						viewModel.onEvent(ValidateNewPasswordSubmit(newPassword))
					},
					keyboardOptions = KeyboardOptions(
						capitalization = KeyboardCapitalization.None,
						keyboardType = KeyboardType.Password,
						imeAction = ImeAction.Next
					),
					keyboardActions = KeyboardActions(
						onNext = { confirmNewPasswordTextField.requestFocus() }
					),
					leadingIcon = R.drawable.ic_lock,
					placeholder = {
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.CenterStart
						) {
							Text(
								text = stringResource(id = R.string.new_password_placeholder),
								style = MaterialTheme.typography.body2,
								fontWeight = FontWeight.Normal,
								color = Gray500
							)
						}
					},
					passwordToggleOption = true,
					isError = isNewPasswordError,
					errorMessage = newPasswordErrorMessage
				)
				Spacer(modifier = Modifier.size(16.dp))
				var confirmNewPassword by remember { mutableStateOf("") }
				GoesToChecklistTextField(
					modifier = Modifier
						.fillMaxWidth()
						.height(48.dp)
						.focusRequester(confirmNewPasswordTextField)
						.bringIntoViewRequester(bringIntoViewRequester)
						.onFocusChanged { focusState ->
							if (focusState.isFocused) {
								coroutineScope.launch {
									delay(250)
									bringIntoViewRequester.bringIntoView()
								}
							}
						},
					textFieldValue = confirmNewPassword,
					onValueChange = {
						isConfirmNewPasswordError = false
						confirmNewPasswordErrorMessage = ""
						confirmNewPassword = it
						viewModel.onEvent(ValidateConfirmNewPasswordSubmit(confirmNewPassword))
					},
					keyboardOptions = KeyboardOptions(
						capitalization = KeyboardCapitalization.None,
						keyboardType = KeyboardType.Password,
						imeAction = ImeAction.Go
					),
					keyboardActions = KeyboardActions(
						onGo = {
							isButtonEnabled = false
							showButtonLoading = true
							keyboardController?.hide()
							viewModel.onEvent(ChangePasswordSubmit)
						}
					),
					leadingIcon = R.drawable.ic_lock,
					placeholder = {
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.CenterStart
						) {
							Text(
								text = stringResource(id = R.string.confirm_new_password_placeholder),
								style = MaterialTheme.typography.body2,
								fontWeight = FontWeight.Normal,
								color = Gray500
							)
						}
					},
					passwordToggleOption = true,
					isError = isConfirmNewPasswordError,
					errorMessage = confirmNewPasswordErrorMessage
				)
				Spacer(modifier = Modifier.size(16.dp))
				GoesToChecklistButton(
					modifier = Modifier
						.fillMaxWidth()
						.height(48.dp),
					buttonText = stringResource(id = R.string.change_password_title),
					isEnable = isButtonEnabled,
					isLoading = showButtonLoading,
					onClick = {
						isButtonEnabled = false
						showButtonLoading = true
						keyboardController?.hide()
						viewModel.onEvent(ChangePasswordSubmit)
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

		if (showSuccessDialog) {
			GoesToChecklistDialog(
				modifier = Modifier
					.width(400.dp)
					.wrapContentHeight()
					.padding(horizontal = 16.dp, vertical = 24.dp),
				textContent = stringResource(id = R.string.change_password_success_content_dialog),
				positiveText = stringResource(id = R.string.ok),
				onPositiveClick = {
					showSuccessDialog = false
					bottomNavController.popBackStack()
				}
			)
		}
	}
}